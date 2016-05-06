// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

var start = require('./start.js');

var set = function (rl, whouse, cbook, menu, urep) {
	rl.removeAllListeners();
	rl.setPrompt('@admin> ');
	rl.on('line', (line) => {
		switch(line.split(" ")[0]) {
		case 'logout':
			console.log('bye!');
			start.logout();
			return;
		case 'show':
			switch(line.split(" ")[1]){
				case 'ingredients':
					whouse.printIngreds();
					break;
				case 'recipes':
					cbook.printBook(whouse);
					break;
				case 'reservations':
					var dayfilter = undefined;
					if (line.trim().split(" ").splice(2)[0] === "-d")
						dayfilter = line.trim().split(" ").splice(2)[1];
					urep.printAllReservations(dayfilter);
					break;
				default:
					console.log('show what?');
			}
			break;
		case 'estimate':
			var amount = line.split(" ")[1];
			var name = line.trim().split(" ").splice(2).join("");
			if (!amount || line.trim().split(" ").length < 3 || isNaN(Number(amount))) {
				console.log('estimate what?');
			} else {
				var ret = whouse.printEstimate(cbook, name, Number(amount));
				if (ret == undefined)
					console.log("What is " + name + "?");
				else if (ret != true)
					console.log("What is " + ret + "? How much does it cost?");
				else {

				}
			}
			break;
		case 'shipment':
			var str = line.trim().split(" ").splice(1).join("").split(",");
			try {
			for (var i = 0; i < str.length; i = i+3) {
				whouse.add(str[i].replace('["',"").replace('"',""),
					Number(str[i+1]),
					Number(str[i+2].replace("]","")),
					Date());
			}
			console.log("present warehouse value: "+ whouse.getTotalValue());
			} catch(err) {
				console.log("Failed. Maybe your syntax is wrong.");
			}
			break;
		case 'menu':
			if (menu.nextConfirmed) {
				console.log("Too late to change 😔");
				break;
			}
			if (line.trim().split(" ")[1] === "-repeat") {
				menu.repeat();
				menu.printNext();
				break;
			}
			var str = line.trim().split(" ").splice(1).join("").split(",");
			try {
			menu.deleteNext();
			for (var i = 0; i < str.length; i = i+3) {
				menu.addNext(str[i].replace('["',"").replace('"',""),
					str[i+1].replace('"',"").replace('"',""),
					Number(str[i+2].replace("]","")),
					Date());
			}
			menu.printNext();
			} catch(err) {
				console.log(menu);
				console.log(err);
				console.log("Failed. Maybe your syntax is wrong.");
			}
			break;
		case 'confirm':
			if (line.trim().split(" ")[1] === "menu") {
				if (!menu.confirm()) console.log("Sombody confirmed it before 😶");
				else console.log("Roger that.");
				break;
			}
		case "timetravel":
			menu.fakeNextWeek();
			console.log("Wooo!");
			break;
		case "finalize":
			if (line.trim().split(" ")[1] === "reservations") {
				menu.close();

				var foods = urep.getFoodCounts();
				for (var i = 0; i < Object.keys(foods).length; i++) {
					console.log(Object.keys(foods)[i] + ": " + foods[Object.keys(foods)[i]]);
				}

				var totalcost = cbook.getTotalCost(whouse, foods);
				var totalsold = menu.getTotalNextPrice(foods);
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