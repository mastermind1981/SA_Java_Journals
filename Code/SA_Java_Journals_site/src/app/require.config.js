// require.js looks for the following global when initializing
var require = {
	baseUrl: ".",
	paths: {
		"bootstrap": "lib/bootstrap.min",
		"signals": "lib/signals.min",
		"hasher": "lib/hasher.min",
		"crossroads": "lib/crossroads.min",
		"jquery": "lib/jquery.min",
		"knockout": "lib/knockout/knockout.min",
		"knockout-mapping": "lib/knockout/knockout.mapping.min",
		"knockout-projections": "lib/knockout/knockout-projections.min",
		"knockout-uploader": "lib/knockout/knockout.uploader",
		"text": "lib/requirejs-text",
		"shalib": "lib/sha256.min",
		"base64": "lib/base64.min",
		"growl": "lib/jquery.bootstrap-growl.min"
    
	},
	shim: {
		"bootstrap": {deps: ["jquery"]},
		"growl": {deps: ["jquery"]}
	}
};
