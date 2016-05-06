// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var Recipe = require('./Recipe.js').Recipe;

var Cookbook = function(parsedJSON) {
	this.recipes = [];

	for (var i = 0; i < parsedJSON.length; i++) {
		this.recipes.push(new Recipe(parsedJSON[i].name, parsedJSON[i].recipe));
	}
};

Cookbook.prototype.printBook = function(whouse) {
	for (var i = 0; i < this.recipes.length; i++) {
		console.log(i + "\t" + this.recipes[i].getString(whouse, "\t"));
	}
};

Cookbook.prototype.find = function(rname) {
	for (var i = 0; i < this.recipes.length; i++) {
		if (this.recipes[i].name === rname)
			return this.recipes[i];
	}

	return undefined;
};

Cookbook.prototype.getIngredients = function(food) {
	var rcp = this.find(food);
	if (!rcp) return undefined;
	return rcp.ingreds;
};

Cookbook.prototype.getTotalCost = function(whouse, customingreds) {
	var total = 0;
	for (var i = 0; i < this.recipes.length; i++) {
		console.log(customingreds);
		console.log(this.recipes[i]);
		console.log(customingreds[this.recipes[i].name]);
		if (customingreds === undefined || customingreds[this.recipes[i].name] != undefined)
			total = total + (this.recipes[i].getCost(whouse) * customingreds[this.recipes[i].name]);
	}

	return total;
};

module.exports.Cookbook = Cookbook;