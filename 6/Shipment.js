// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var Shipment = function(ingredientName, amount, price, date) {
	this.ingname = ingredientName.split(" ").join("");
	this.amount = amount;
	this.price = price;
	this.date = date;
}

module.exports.Shipment = Shipment;