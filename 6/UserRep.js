// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

const User = require('./User.js').User;

var UserRep = function() {
	this.users = [];
}

UserRep.prototype.add = function(username, password) {
	this.users.push(new User(username, password));
};

UserRep.prototype.addAll = function(newones) {
	for (var i = 0; i < newones.length; i++) {
		this.add(newones[i]['username'], newones[i]['password']);
	}
};

UserRep.prototype.find = function(username) {
	for (var i = this.users.length - 1; i >= 0; i--) {
		if (this.users[i]['username'] == username)
			return this.users[i];
	}

	return undefined;
};

UserRep.prototype.authenticate = function(username, password) {
	var target = this.find(username);
	if (target == undefined) return undefined;
	else return target.authenticate(password);
};

UserRep.prototype.printAllReservations = function(dayfilter) {
	console.log("customer\t\tmeal\t\tday");
	for (var i = 0; i < this.users.length; i++) {
		var keys = [];
		if (dayfilter === undefined) keys = Object.keys(this.users[i].reservations);
		else if (this.users[i].reservations[dayfilter] != undefined) keys = [dayfilter];
		for (var j = 0; j < keys.length; j++) {
			console.log(this.users[i].username + "\t\t" + this.users[i].reservations[keys[j]].food + "\t\t" + keys[j]);
		}
	}
};

UserRep.prototype.getFoodCounts = function() {
	var foodCounts = {};
	for (var i = 0; i < this.users.length; i++) {
		var keys = Object.keys(this.users[i].reservations);
		for (var j = 0; j < keys.length; j++) {
			var foodname = this.users[i].reservations[keys[j]].food;
			if (foodCounts[foodname] == undefined) foodCounts[foodname] = 0;
			foodCounts[foodname] = foodCounts[foodname] + 1;
		}
	}

	return foodCounts;
};


module.exports.UserRep = UserRep;
