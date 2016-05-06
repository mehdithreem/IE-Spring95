// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var Shipment = require('./Shipment.js').Shipment;

var Warehouse = function() {
	this.storage = [];
};

Warehouse.prototype.add = function(ingredientName, amount, price, date) {
	this.storage.push(new Shipment(ingredientName, amount, price, date));
};

Warehouse.prototype.addAll = function(newShips) {
	dates = Object.keys(newShips);
	for (var i = 0; i < dates.length; i++) {
		shipmentdate = new Date(dates[i].split('-')[2], dates[i].split('-')[1], dates[i].split('-')[0],0,0,0,0);
		contents = newShips[dates[i]];
		for (var j = contents.length - 1; j >= 0; j--) {
			this.add(contents[j].ingredient.name, contents[j].amount, contents[j].price, shipmentdate);
		}
	}
};

Warehouse.prototype.getUnitPrice = function(target) {
	var lastest = undefined;

	for (var i = 0; i < this.storage.length; i++) {
		if (this.storage[i].ingname === target) {
			if (lastest == undefined || lastest.date > this.storage[i].date) lastest = this.storage[i];
		}
	}

	if (lastest == undefined) return undefined;
	return lastest.price;
};

Warehouse.prototype.getLastestUnitPrice = function(target) {
	var lastest = undefined;

	for (var i = 0; i < this.storage.length; i++) {
		if (this.storage[i].ingname === target) {
			if (lastest == undefined || lastest.date < this.storage[i].date) lastest = this.storage[i];
		}
	}

	if (lastest == undefined) return undefined;
	return lastest.price;
};

Warehouse.prototype.getIngreds = function() {
	var total = {};
	for (var i = this.storage.length - 1; i >= 0; i--) {
		if (total[this.storage[i].ingname] == undefined)
			total[this.storage[i].ingname] = {};
		total[this.storage[i].ingname].amount = (total[this.storage[i].ingname].amount||0)+ this.storage[i].amount;
		total[this.storage[i].ingname].price = (total[this.storage[i].ingname].price||0)+ this.storage[i].price;
	}

	return total;
};

Warehouse.prototype.printIngreds = function() {
	var total = this.getIngreds();
	var sortedKeys = Object.keys(total).sort();

	for (var i = 0; i < sortedKeys.length; i++) {
		console.log(i + '\t' + sortedKeys[i] +'\t\t\t\t'+ total[sortedKeys[i]].amount + '\t' + total[sortedKeys[i]].price);
	}
};

Warehouse.prototype.estimate = function(cbook, dealname, count) {
	var rcp = cbook.getIngredients(dealname.split(" ").join(""));
	if (rcp==undefined) return undefined;
	var totalpprice = 0;

	console.log("ingredient\trequired\tavailable\tpurchase price");
	for (var i = 0; i < rcp.length; i++) {
		if (!this.getIngreds()[rcp[i].ingname])
			return rcp[i].ingname;
		var avail = this.getIngreds()[rcp[i].ingname].amount;
		var req = rcp[i].amount * count;
		var pprice = 0;
		if (avail - req < 0)
			pprice = (req - avail) * this.getLastestUnitPrice(rcp[i].ingname);
		console.log(rcp[i].ingname + "\t\t" + req + "\t\t" + avail + "\t\t" + pprice);
		totalpprice += pprice;
	}
	console.log("\t\t\t\t\t\t"+totalpprice);

	return true;
};

Warehouse.prototype.getTotalValue = function() {
	var total = 0;
	for (var i = 0; i < this.storage.length; i++) {
		total += this.storage[i].price * this.storage[i].amount;
	}

	return total;
};

module.exports.Warehouse = Warehouse;