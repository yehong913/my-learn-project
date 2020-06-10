/*$(document).ready(function(){
	debugger;
	setTimeout();
	document.onreadystatechange = loadPage;
});

function loadPage(){
	if(document.readyState == 'complete'){
	    alert(1);
		initPages();
		initData();
		setDefaultItem();
	}
}*/

window.onload = function(){
    setTimeout(function(){
        initData();
        initPages();
        setDefaultItem();
    },500);
}