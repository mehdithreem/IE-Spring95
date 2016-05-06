// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var Menu = function() {
	this.currWeek = {};
	this.nextWeek = {};
	this.nextConfirmed = false;
	this.counter = 0;
	this.open = false;
	this.faked = false;
	this.timer = setTimeout(function() {
		if (Date.getDay() == 0 && Date.getHours() == 0) {
			if (this.faked != true)
				this.fakeNextWeek();
			this.faked = false;
		}
	}, 1000*60*60);
};

Menu.prototype.fakeNextWeek = function() {
	this.currWeek = this.nextWeek;
	this.nextWeek = {};
	this.nextConfirmed = false;
	this.faked = true;
};

Menu.prototype.addNext = function(day, dealname, dealprice) {
	if (this.nextWeek[day] == undefined) this.nextWeek[day] = [];
	this.nextWeek[day].push({name: dealname.split(" ").join(""), price: dealprice});
};

Menu.prototype.deleteNext = function() {
	this.nextWeek = {};
};

Menu.prototype.repeat = function() {
	this.nextWeek = this.currWeek;
};

Menu.prototype.printNext = function() {
	var that = this;
	var printDay = function(dayname) {
		console.log(dayname);
		if (that.nextWeek[dayname] == undefined || that.nextWeek[dayname].length == 0) console.log("-")
		else for (var i = 0; i < that.nextWeek[dayname].length; i++) {
			console.log("- " + that.nextWeek[dayname][i].name + " (" + that.nextWeek[dayname][i].price + ")");
		}
	}

	printDay("SAT");
	printDay("SUN");
	printDay("MON");
	printDay("TUE");
	printDay("WED");
};

Menu.prototype.confirm = function() {
	this.open = true;
	if (this.nextConfirmed) return false;
	this.nextConfirmed = true;
	return true;
};

Menu.prototype.close = function() {
	this.open = false;
};

Menu.prototype.isOpen = function() {
	return this.open && this.nextConfirmed;
};

Menu.prototype.haveFood = function(dayname, foodname) {
	if (this.nextWeek[dayname] == undefined)
		return false;

	for (var i = 0; i < this.nextWeek[dayname].length; i++) {
		if (this.nextWeek[dayname][i].name === foodname)
			return true;
	}

	return false;
};

Menu.prototype.getTotalNextPrice = function(foods) {
	var total = 0;
	for (var i = 0; i < Object.keys(this.nextWeek).length; i++) {
		for (var j = 0; j < this.nextWeek[Object.keys(this.nextWeek)[i]].length; j++) {
			var meal = this.nextWeek[Object.keys(this.nextWeek)[i]][j];
			total = total + Number(meal.price * foods[meal.name]);
		}
	}

	return total;
};

Menu.prototype.getCounter = function() {
	this.counter++;
	return this.counter;
};

module.exports.Menu = Menu;