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
		"text": "lib/requirejs-text"
	},
	shim: {
		"bootstrap": {deps: ["jquery"]}
	},
	config: {
		'dat': {
			SERVER: 'http://localhost:8080'
		}
	}
};
