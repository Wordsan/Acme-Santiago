function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function setSessionCookie(cname, cvalue) {
	var expires = "expires=" + 0;
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for ( var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) { return c.substring(name.length, c.length); }
	}
	return "";
}

$(document).ready(function() {

	if (getCookie('cookiesNotice') == '') {
		$('#cookie-notice').removeClass('hidden');
	}

	$('#cookie-notice-button').click(function() {
		if (getCookie('cookiesNotice') == '') {
			setCookie('cookiesNotice', true, 365 * 5);
		}
		$('#cookie-notice').addClass('hidden');
	});

	$('#cookie-notice #close-button').click(function() {
		$('#cookie-notice').addClass('hidden');
	});

});
