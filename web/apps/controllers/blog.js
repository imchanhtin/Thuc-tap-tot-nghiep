var express = require("express");
var router = express.Router();

router.get("/",function(req,res){
    res.json({"message":"Day la trang blog"});
    res.render("blog/index");
});
module.exports=router;