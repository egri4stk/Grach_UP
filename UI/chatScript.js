
var username = "";
var id = 0;
var mesHistory = [];
function run(){
	var appContainer = document.getElementsByClassName('all')[0];
	appContainer.addEventListener('click', delegateMessage);
	appContainer.addEventListener('keydown', delegateMessage);
	
	username = loadUsername() || "Anonymous";
	var input = document.getElementById('nameInput');
	input.value = username;
	var currentUser = document.getElementById('current');
   	currentUser.innerHTML = username;
   	
   	mesHistory = loadHistory() || [newMessage('Hi')];
   	id = mesHistory[mesHistory.length - 1].idi;
   	updateHistory(mesHistory);
   	var content = document.getElementById("chat")
	content.scrollTop +=9999;
}

function delegateMessage(evtObj) {
	if(evtObj.type === 'click' && evtObj.target.classList.contains('changeButton')) {
		changeName(evtObj);
	}
	if( (evtObj.type === 'click' && evtObj.target.classList.contains('mesButton')) || 
		(evtObj.type === 'keydown' && evtObj.target.classList.contains('fieldMessage') && evtObj.keyCode == 13)){
		sendMessage(evtObj);
	}
}

function changeName() {
	var input = document.getElementById('nameInput');
    var name = document.getElementsByClassName('myName');
    var delBtn = document.getElementsByClassName('del');
    var editBtn = document.getElementsByClassName('edit');
   	var messages = document.getElementsByClassName('item');
    var editInput = document.getElementsByClassName('In');
    var btnCancel = document.getElementsByClassName('cancel');
    var delStatus = document.getElementsByClassName('delText');
    
   	
   	var tempUsername ="";
   	tempUsername= input.value;
   	username = tempUsername.replace(/(^\s+|\s+$)/g,'');
   	
   	if(username==""){
   		username="Anonymous";
   		input.value="Anonymous";
   	}
   	var currentUser = document.getElementById('current');
   	currentUser.innerHTML = username;
   	saveUsername(username);
   	
   	for(var i=0; i< editInput.length; i++){
   		editInput[i].hidden = true;
   	}
   	for(var i=0; i< btnCancel.length; i++){
   		btnCancel[i].style.display = 'none';
   		
   	}
   	
   	
   	for(var i=0; i < messages.length; i++ ){
   		if(name[i].innerHTML!=username){
   		 	delBtn[i].style.display = 'none';	
   			editBtn[i].style.display = 'none';	
   	    }
   	    else{
			if(delStatus[i].innerHTML!="Удалено"){
			delBtn[i].style.display = 'inline';	
   			editBtn[i].style.display = 'inline';
   			}	  			
   	    }
   	}

}

function sendMessage() {
	var todoText = document.getElementById('fieldMessage');
	var divItem = document.createElement('li');
	var divName = document.createElement('li');
	var textName = document.createElement('div');
	var text = document.createElement('div');
	var time = document.createElement('div');
	var but1 = document.createElement('button');
	var but2 = document.createElement('button');
	var input = document.createElement('input');
   	var btnCancel = document.createElement('button');
   	var delStatus = document.createElement('div');
	var editStatus = document.createElement('div');
	
	but1.classList.add('del');
	but2.classList.add('edit');
	btnCancel.classList.add('cancel');
	time.classList.add('time');
	divItem.classList.add('item');
	textName.classList.add('myName');
	delStatus.classList.add('delText');
	editStatus.classList.add('editText');
	input.classList.add('inputMessage');
	

	time.setAttribute('id', 't');
	divItem.setAttribute('id', 'divId' + id);
	text.setAttribute('id', 'textDiv' + id);
	delStatus.setAttribute('id', 'textDel' + id);
	editStatus.setAttribute('id', 'textEdit' + id);
	input.setAttribute('id', 'textIn' + id);
	input.setAttribute('class', 'In');
	but1.setAttribute('id','del' + id);
    but2.setAttribute('id','red' + id);
    btnCancel.setAttribute('id','cancel' + id);
	
  	delStatus.innerHTML="";
  	editStatus.innerHTML="";
	var str = new String(id);
	text.innerHTML = todoText.value;
	textName.innerHTML = username;
	time.textContent = new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");

	but1.addEventListener('click', function(){
		deleteMessage(str);
	});

	but2.addEventListener('click', function(){
		changeMessage(str);
	});
	btnCancel.addEventListener('click',function(){
		cancelEditMessage(str);
	});
	
	divItem.appendChild(but1);
	divItem.appendChild(but2);
	divItem.appendChild(input);
	divItem.appendChild(btnCancel);
	divItem.appendChild(text);
	divItem.appendChild(delStatus);
	divItem.appendChild(editStatus);
	divName.appendChild(textName);
	divName.appendChild(time);

	
	input.hidden = true;
	
	btnCancel.style.display = 'none';	

	if(todoText.value != "") {
		document.getElementById('list').appendChild(divName);
		document.getElementById('list').appendChild(divItem);
	}

	todoText.value = "";
	id++;
	var content = document.getElementById("chat")
	content.scrollTop +=9999;
}


function deleteMessage(id) {
	var text = document.getElementById('textDiv' + id);
	var delStatus = document.getElementById('textDel' + id);
	var input = document.getElementById('textIn' + id);
	var btnCancel = document.getElementById('cancel'+id);
	var btnEdit = document.getElementById('del'+id);
	var btnDel = document.getElementById('red'+id);
	var item = document.getElementById('divId'+id);
	var editStatus = document.getElementById('textEdit' + id);
	text.innerHTML="";
	btnCancel.style.display = 'none';
	btnEdit.style.display = 'none';
	btnDel.style.display = 'none';
	delStatus.innerHTML="Удалено";
	item.classList.add('deletedMes');
	editStatus.innerHTML="";
}

function changeMessage(id) {
	var text = document.getElementById('textDiv' + id);
	var input = document.getElementById('textIn' + id);
	var btnCancel = document.getElementById('cancel'+id);
	var editStatus = document.getElementById('textEdit' + id);
	input.value = text.innerHTML ;
     
	input.hidden = false;
	btnCancel.style.display = 'inline';	

	input.addEventListener('keydown', function(e) {
		if(e.keyCode == 13) {
			var tempMes ="";
   			tempMes= input.value;
   			var mes="";
   			mes = tempMes.replace(/(^\s+|\s+$)/g,'');
			if(mes!="" && mes!=text.innerHTML){
			text.innerHTML = mes ;
			editStatus.innerHTML = "edit:" + new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
				}
			input.hidden = true;
			btnCancel.style.display = 'none';	
			
		}
	});		
}
function cancelEditMessage(id){
  	var k = document.getElementById('divId' + id);
	var text = document.getElementById('textDiv' + id);
	var input = document.getElementById('textIn' + id);
	var btnCancel = document.getElementById('cancel'+id);
			input.hidden = true;
			btnCancel.style.display = 'none';

}
function newMessage(text) {
    id++;

    return {
        name: username,
        idi: id,
        text: text,
        time: new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1"),
        deleted: false
    };
}
function loadUsername() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem("username");
	return item;
}
function saveUsername(name) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem("username", name);
}

function loadHistory() {
    if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
    var item = localStorage.getItem("mesHistory");
    return item && JSON.parse(item);
}

function saveHistory(messageHistory) {
    if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

    localStorage.setItem("mesHistory", JSON.stringify(messageHistory));
}
function updateHistory(messageHistory) {
	document.getElementById('list').innerHTML = "";
    for (var i = 0; i < mesHistory.length; i++) {
    	showHistory(mesHistory[i]);
    }
    saveHistory(mesHistory);
}

