function insertAfter(newNode, referenceNode) {
	console.log("insertAfter med " + newNode + ", " + referenceNode);
	console.log("parent: " + referenceNode.parentNode);
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

//Looks for a <select> with id "genre" and fills it with <option>'s of each genre in db
//Using the controller method at /rest/genres/allJson
//GenreController -> getGenres()
function getGenresAjaxToSelect(){

	var sel = document.getElementById("genre");

	$.getJSON("/rest/genres/allJson", function(data){

		$('#genre').text('');
		for(var index in data){
			$('#genre').append('<option>'+data[index].name+'</option>')
			}
		
	});
}

function removeFieldById(id){
	alert("removeFieldById " + id);
	var field = document.getElementById(id);
	field.parentNode.removeChild(field);
}