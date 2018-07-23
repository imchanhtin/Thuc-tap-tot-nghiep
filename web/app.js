var express = require("express");
var controllers = require(__dirname + "/apps/controllers");
var config = require("config");
var bodyParser = require("body-parser");
var app = express();

app.use(controllers);
app.use(bodyParser.json());

app.set("views", __dirname + "/apps/views");
app.set("view engine", "ejs");

//Static Folder
app.use("/static",express.static(__dirname + "/public"));

var host = config.get("server.host");
var port = config.get("server.port");
app.listen(port,host, function () {
    console.log("Server dang chay tren port",port);
});