var http = require("https");

var options = {
  "method": "GET",
  "hostname": "calm-beyond-73982.herokuapp.com",
  "port": null,
  "path": "/userdata",
  "headers": {
    "content-type": "application/json",
    "cache-control": "no-cache",
    "postman-token": "49c51aee-f049-9e8a-f988-c7c05f588d6e"
  }
};


var fs = require('fs');
var exportBase ='';



var req = http.request(options, function (res) {
  var chunks = [];

  res.on("data", function (chunk) {
    chunks.push(chunk);
  });

  res.on("end", function () {
    var body = Buffer.concat(chunks);
    //console.log(body.toString());
	
	fs.writeFileSync(exportBase + 'index.json', body.toString());
	
	//script compteurs exos
	/*
	
	var mapExo = new Map();
	
	console.log('bla');
	
	
	/*for (i in JSON.stringify(body)) {
		
		console.log(i);
		
	}
	
	
	console.log('bla');
	*/
	
	
	
  });
});



req.end();
