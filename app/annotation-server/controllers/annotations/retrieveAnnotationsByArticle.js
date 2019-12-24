const { Annotation, ETag } = require("../../models");

module.exports = async (req, res, next) => {
  let instances = await Annotation.find({
    articleId: req.params.articleId
  }).lean();
  instances = await Promise.all(instances.map(async anno => {
    const { etag } = await ETag.findOne({ annotationId: anno._id }).lean();
    return { ...anno, ETag: etag };
  }));
  res.json(instances);
};
