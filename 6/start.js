// Internet Engineering Course - University of Tehran - Spring 2016
// Computer Assignment 6
// M.Mahdi Mahdizadeh (ID:810192558)
// Nahal Mirzaie (ID:810192489)

const UserRep = require('./UserRep').UserRep;
const Warehouse = require('./Warehouse.js').Warehouse;
const Cookbook = require('./Cookbook.js').Cookbook;
const Menu = require('./Menu.js').Menu;
const adminEnv = require('./adminEnv.js');
const userEnv = require('./userEnv.js');
const JSONparser = require('./JSONparser.js');
const readline = require('readline');

var rl = readline.createInterface(process.stdin, process.stdout);

var recursiveAsyncLoginLoop = function () {
	rl.question('username: ', function (user) {
		var userObj = urep.find(user);
		if (userObj == undefined) {
			console.log('Username "' + user + '" not exist.');
		} else {
			rl.question('password: ', function (pass) {
				var status = urep.authenticate(user,pass);
				if (status === true) {
					console.log('Welcome Back, ' + user + '!');
					if (user == 'admin')
						adminEnv.set(rl, whouse, cbook, menu, urep);
					else
						userEnv.set(rl, userObj, menu);
				} else if (status === false) {
					console.log('Your account has been locked.');
					return recursiveAsyncLoginLoop();
				} else {
					console.log('Wrong password. (attemp#' + status + ').');
					return recursiveAsyncLoginLoop();
				}
			});
		}
		return recursiveAsyncLoginLoop();
	});
};

var logout = function () {
	rl.setPrompt('');
	rl.removeAllListeners('line');
	recursiveAsyncLoginLoop();
};


var urep = new UserRep();
urep.add('admin','password');
urep.addAll(JSONparser.parse('./data/users.json'));

var whouse = new Warehouse();
whouse.addAll(JSONparser.parse('./data/warehouse.json'));

var cbook = new Cookbook(JSONparser.parse('./data/recipes.json'));

var menu = new Menu();

rl.on('close', () => {
	console.log('\nHave a great day!');
	process.exit(0);
});
recursiveAsyncLoginLoop();

module.exports.logout = logout;