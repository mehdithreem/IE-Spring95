// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var Recipe = function(name, ings) {
	this.name = name.split(" ").join("");
	this.ingreds = [];

	for (var i = ings.length - 1; i >= 0; i--) {
		this.ingreds.push({ingname: ings[i].ingred.name.split(" ").join(""), amount: ings[i].amount});
	}
};

Recipe.prototype.getString = function(whouse, delim) {
	var str = this.name + "\n" + delim;
	var mealprice = 0;
	for (var i = 0; i < this.ingreds.length; i++) {
		mealprice = mealprice + (whouse.getUnitPrice(this.ingreds[i].ingname) * this.ingreds[i].amount);
		str += this.ingreds[i].ingname + ": " + this.ingreds[i].amount;
		if(i != this.ingreds.length - 1) str += ", ";
	}
	str += "\n" + delim + mealprice;

	return str;
};

Recipe.prototype.getCost = function(whouse) {
	var mealprice = 0;
	for (var i = 0; i < this.ingreds.length; i++) {
		mealprice = mealprice + (whouse.getUnitPrice(this.ingreds[i].ingname) * this.ingreds[i].amount);
	}

	return mealprice;
};

module.exports.Recipe = Recipe;