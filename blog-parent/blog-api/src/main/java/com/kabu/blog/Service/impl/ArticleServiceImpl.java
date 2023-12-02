package com.kabu.blog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabu.blog.Service.*;
import com.kabu.blog.dao.dos.Archive;
import com.kabu.blog.dao.mapper.ArticleBodyMapper;
import com.kabu.blog.dao.mapper.ArticleMapper;
import com.kabu.blog.dao.mapper.ArticleTagMapper;
import com.kabu.blog.dao.pojo.Article;
import com.kabu.blog.dao.pojo.ArticleBody;
import com.kabu.blog.dao.pojo.ArticleTag;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.utils.DateUtils;
import com.kabu.blog.utils.UserThreadLocal;
import com.kabu.blog.vo.ArticleBodyVo;
import com.kabu.blog.vo.ArticleVo;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.TagVo;
import com.kabu.blog.vo.params.ArticleParam;
import com.kabu.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        //分页查询article数据库表,得到结果
//        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        //查询条件
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        //查询文章的参数 加上分类id，判断不为空 加上分类条件
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        //根据文章标签来进行查看文章
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null){
//            //一个文章有许多标签
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0){
//                //查询的文章id
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//        }
//        //根据置顶和排序时间来进行排序
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        //对数据进行处理
//        List<ArticleVo> articleVoList=copyList(records,true,true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticle(int limit) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(articles);
    }

    @Override
    public Result listArchives() {
        List<Archive> archives=articleMapper.listArchives();
        return Result.success(archives);
    }

    @Override
    public Result newArticles(int limit) {
        //最新文章
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from ms_article group by create_date limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(articles);
    }

    @Override
    public Result findByArticleId(Long id) {
        /**
         * 1.根据id查询文章信息
         * 2.关联表
         */
        Article article = this.articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true,true,true);

        //文章阅读数
        //线程池
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    /**
     * 发布文章 构建Article对象
     * 1.作者id 当前登录用户
     * 2.标签 加入到关联列表中
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {

        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        boolean isEdit=false;
        if(articleParam.getId()!=null){
            article=new Article();
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(Long.parseLong(String.valueOf(articleParam.getCategory().getId())));
            articleMapper.updateById(article);
            isEdit=true;
        }else {
            //需要生成一个文章id
            article.setAuthorId(sysUser.getId());
            article.setCategoryId(articleParam.getCategory().getId());
            article.setCreateDate(System.currentTimeMillis()/1000);
            System.out.println(System.currentTimeMillis()/1000);
            article.setCommentCounts(0);
            article.setSummary(articleParam.getSummary());
            article.setTitle(articleParam.getTitle());
            article.setViewCounts(0);
            article.setWeight(Article.Article_Common);
            article.setBodyId(-1L);
            this.articleMapper.insert(article);
        }


        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);
        //article和articlebody数据库同时更新
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);

    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean category) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,category));
        }
        return articleVoList;
    }
    @Autowired
    private CategoryService categoryService;
    public ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean category){
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        Long createDate = article.getCreateDate();
        long milliseconds = createDate * 1000; // 将秒级时间戳转换为毫秒级时间戳

        DateTime dateTime = new DateTime(milliseconds);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = dateTime.toString(formatter);
        articleVo.setCreateDate(formattedDateTime);
        //并不是所以的都有标签和作者
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        //是否存在内容
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleById(bodyId));
        }
        //是否存在分类
        if(category){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findcategoryByid(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    public ArticleBodyVo findArticleById(Long bodyId) {
        //文章详情
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
