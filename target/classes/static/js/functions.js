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

//used for addBook2.html and editBook2.html
function applyNoGenres(formId){

	if(!document.getElementById(formId)){
		alert("applyNoGenres could not find form of id " + formId);
		return false;
		}

	var form = document.getElementById(formId);

	if(form.querySelector("#noGenre")){
		var checkb = form.querySelector("#noGenre");
		//on edit page
		if(checkb.checked){
			if(document.getElementById("genre")){
				document.getElementById("genre").disabled = true;
			}
		}
		
		//switch for have genre found
		
		checkb.addEventListener("click", function(){toggleGenre(this.checked)});
		form.addEventListener("submit", function(){if(noGenreChecked())removeFieldById('genre')}, true);				
		}
	else{
		alert("function applyNoGenres could not find the noGenre switch");
		return false;
		}
}

function toggleGenre(isChecked){
	if(!document.getElementById("genre")){
		console.log("toggleGenre could not find element by id 'genre'");
		return false;
		}
	if(isChecked){
		document.getElementById("genre").disabled = true;
		}
	else{
		console.log("No");
		document.getElementById("genre").disabled = false;
		}
}

function noGenreChecked(){
	if(form.querySelector("#noGenre")){
		var checkb = form.querySelector("#noGenre");
		return checkb.checked;
	}
	return false;
}

/*Sends a form for deleting ... this needs a form in document
 * <form name="myform" th:action="@{/books/deleteBook}" method="post" class="hidden">
  <input type="hidden" name="id" id="id"/>
</form>
*/
function sendFormX(id){
	document.querySelector("form[name='myform']  > input").value = id;
	document.querySelector("form[name='myform']").submit();
}