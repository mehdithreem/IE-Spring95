// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var start = require('./start.js');

var set = function (rl, user, menu) {
	rl.removeAllListeners();
	rl.setPrompt('@user> ');
	rl.on('line', (line) => {
		switch(line.split(" ")[0]) {
		case 'logout':
			console.log('bye!');
			start.logout();
			return;
		case 'show':
			switch(line.split(" ")[1]){
				case 'menu':
					if (menu.isOpen())
						menu.printNext();
					else
						console.log("Checkout later.");
					break;
				case 'reservations':
					user.printReservations();
					break;
				default:
					console.log('show what?');
			}
			break;
		case 'reserve':
			var daystr = line.trim().split(" ")[1];
			var foodstr = line.trim().split(" ").splice(2).join("");
			if (!daystr || !foodstr) {
				console.log("reserve what??");
				break;
			}
			else if (!menu.isOpen()) {
				console.log("Sorry, you cannot ☹️");
				break;
			}
			else if (!menu.haveFood(daystr, foodstr)) {
				console.log("We don't have " + foodstr + " on " + daystr);
				break;
			} 
			else {
				user.reserve(daystr, foodstr, menu.getCounter());
				break;
			}
		default:
			console.log('what?');
			break;
		}
		rl.prompt();
	});
	rl.prompt();
};

module.exports.set = set;