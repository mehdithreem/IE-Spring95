// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var User = function(username, password) {
	this.username = username;
	this.password = password;
	this.locked = false;
	this.attemps = 0;
	this.reservations = {"SAT":{food: "chicken-kebab", ref:"100"}};
}

User.prototype.reserve = function(dayname, mealname, code) {
	var changed = false;
	if (this.reservations[dayname] == undefined) changed = true;
	this.reservations[dayname] = {food: mealname, ref:code};

	return changed;
};

User.prototype.printReservations = function(first_argument) {
	var keys = Object.keys(this.reservations);
	if (keys.length == 0) {
		console.log("No reservations for next week.");
		return;
	}
	console.log("reference\t\tday\t\tmeal\t\t");
	for (var i = 0; i < keys.length; i++) {
		console.log(this.reservations[keys[i]].ref + "\t\t" + keys[i] + "\t\t" + this.reservations[keys[i]].food);
	}
};

User.prototype.authenticate = function(password) {
	if (this.locked)
		return false;
	if (this.password === password) {
		this.attemps = 0;
		return true;
	}
	else {
		this.attemps += 1;
		if (this.attemps >= 3) {
			this.locked = true;
			var that = this;

			setTimeout(function() {
				that.locked = false;
				that.attemps = 0;
			}, 1000*120);
		}
		return this.attemps;
	}
};

module.exports.User = User;
