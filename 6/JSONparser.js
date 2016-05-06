// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var fs = require("fs");

var parse = function(fileName) {
	var contents;
	var jsonContent;
	try {
		contents = fs.readFileSync(fileName);
		jsonContent = JSON.parse(contents);
	} catch (err) {
		console.log("JSONparser: " + err);
		return;
	}
	
	return jsonContent;
}

module.exports.parse = parse;
