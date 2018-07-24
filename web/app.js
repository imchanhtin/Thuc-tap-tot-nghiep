var express = require("express");
var controllers = require(__dirname + "/apps/controllers");
//var config = require("config");
var bodyParser = require("body-parser");
var app = express();

app.use(controllers);
app.use(bodyParser.json());

app.set("views", __dirname + "/apps/views");
app.set("view engine", "ejs");

//Static Folder
app.use("/static",express.static(__dirname + "/public"));
app.listen(process.env.PORT|| 3000);
app.get("/",function(req,res){
    res.render("index");
});