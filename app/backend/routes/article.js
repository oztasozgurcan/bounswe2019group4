const express = require('express');
const router = express.Router();
const articleController = require('../controllers/article')
const {modelBinder, multipleModelBinder} = require('../controllers/db')
const {isAuthenticated} = require('../controllers/auth')
const { Article } = require('../models/article')
const { ArticleUser } = require('../models/article-user')


/*
  Post endpoint for article.
  Check controller function for more detail
*/

router.post('/', [ isAuthenticated, modelBinder(Article, 'Article')], articleController.postArticle)

/*
  Post endpoint for updating article.
  Check controller function for more detail
*/

router.patch('/:id', [ isAuthenticated, modelBinder(Article, 'Article')], articleController.editArticle)

/*
  Post endpoint for rating article.
  Check controller function for more detail
*/

router.post('/:id/rate', [ isAuthenticated, multipleModelBinder([
  [Article, 'Article'],
  [ArticleUser, 'ArticleUser']
])], articleController.rateArticle)

/*
  Get endpoint for article.
  Check controller function for more detail
*/
router.get('/:id', modelBinder(Article, 'Article'), articleController.getArticle)

/*
  Delete endpoint for article.
  Check controller function for more detail
*/
router.delete('/:id', [isAuthenticated, multipleModelBinder([
  [Article, 'Article'],
  [ArticleUser, 'ArticleUser']
])], articleController.deleteArticle)

module.exports = router