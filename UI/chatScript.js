
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
   	
   	mesHistory = loadHistory() || [newMessage('first message')];
   	id = mesHistory[mesHistory.length - 1].idi;
   	updateHistory(mesHistory);
   	fixScroll();		
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
function showHistory(message){
	var divBox = document.createElement('li');
	var divItem = document.createElement('div');
	var divName = document.createElement('div');
	var textName = document.createElement('div');
	var text = document.createElement('div');
	var time = document.createElement('div');
	var btnDel = document.createElement('button');
	var btnEdit = document.createElement('button');
	var input = document.createElement('input');
   	var btnCancel = document.createElement('button');
   	var delStatus = document.createElement('div');
	var editStatus = document.createElement('div');

	btnDel.classList.add('del');
	btnEdit.classList.add('edit');
	btnCancel.classList.add('cancel');
	time.classList.add('time');
	divItem.classList.add('item');
	divName.classList.add('divBox')
	textName.classList.add('myName');
	delStatus.classList.add('delText');
	editStatus.classList.add('editText');
	input.classList.add('inputMessage');

	time.setAttribute('id', 't');
	divItem.setAttribute('id', 'divId' + message.idi);
	text.setAttribute('id', 'textDiv' + message.idi);
	delStatus.setAttribute('id', 'textDel' + message.idi);
	editStatus.setAttribute('id', 'textEdit' + message.idi);
	input.setAttribute('id', 'textIn' + message.idi);
	input.setAttribute('class', 'In');
	btnDel.setAttribute('id','del' + message.idi);
    btnEdit.setAttribute('id','red' + message.idi);
    btnCancel.setAttribute('id','cancel' + message.idi);
    
    divItem.appendChild(btnDel);
	divItem.appendChild(btnEdit);
	divItem.appendChild(input);
	divItem.appendChild(btnCancel);
	divItem.appendChild(text);
	divItem.appendChild(delStatus);
	divItem.appendChild(editStatus);
	divName.appendChild(textName);
	divName.appendChild(time);
	input.hidden = true;
	btnCancel.style.display = 'none';
	divBox.appendChild(divName);
	divBox.appendChild(divItem);	
	document.getElementById('list').appendChild(divBox);

    
	if(message.deleted==true){
    	delStatus.innerHTML="Удалено";
    	btnDel.style.display = 'none';	
   		btnEdit.style.display = 'none';
   		divItem.classList.add('deletedMes');
	}
  	else{
  		if(message.edited==true){
  		editStatus.innerHTML = "edited";
  			message.edited==false;
  		}
  		if(message.name != username){
  			btnDel.style.display = 'none';	
   			btnEdit.style.display = 'none';
  		}
  	}
	
	textName.innerHTML = message.name;
	time.innerHTML = message.time;
	text.innerHTML = message.text;
	
	btnDel.addEventListener('click', function(){
		deleteMessage(message);
	});

	btnEdit.addEventListener('click', function(){
		changeMessage(message);
	});
	btnCancel.addEventListener('click',function(){
		cancelEditMessage(message);
	});
		
}

function changeName() {
	var input = document.getElementById('nameInput'); 	
    var tempUsername ="";
   	tempUsername= input.value;
   	username = tempUsername.replace(/(^\s+|\s+$)/g,'');
   	var currentUser = document.getElementById('current');
   	currentUser.innerHTML = username;
   	if(username==""){
   		username="Anonymous";
   		input.value="Anonymous";
   		currentUser.innerHTML = "Anonymous";
   	}
    saveUsername(username);
    updateHistory(mesHistory);
    

}

function sendMessage() {
	var inputText = document.getElementById('fieldMessage');
	var str = inputText.value;
	if(str != "") {
    	mesHistory.push(newMessage(str));
    	updateHistory(mesHistory);
    	inputText.value = "";
    }
	fixScroll();	
}


function deleteMessage(message) {
	message.text = "";
	message.deleted = true;
	updateHistory(mesHistory);	
}

function changeMessage(message) {
	
	var input = document.getElementById('textIn' + message.idi);
	var btnCancel = document.getElementById('cancel'+message.idi);

	input.value = message.text.replace(/(^\s+|\s+$)/g,'') ;
	input.hidden = false;
	btnCancel.style.display = 'inline';	

	input.addEventListener('keydown', function(e) {
		if(e.keyCode == 13) {
			var tempMes ="";
   			tempMes= input.value;
   			var mes="";
   			mes = tempMes.replace(/(^\s+|\s+$)/g,'');
			if(mes!="" && mes!=message.text){
				message.text = mes;
				message.edited=true;
				updateHistory(mesHistory);
				
			}
			input.hidden = true;
			btnCancel.style.display = 'none';
				
		}
	});		
}
function cancelEditMessage(message){
  	var k = document.getElementById('divId' + message.idi);
	var text = document.getElementById('textDiv' + message.idi);
	var input = document.getElementById('textIn' + message.idi);
	var btnCancel = document.getElementById('cancel'+ message.idi);
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
        deleted: false,
        edited: false
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
function fixScroll(){
	var content = document.getElementById("chat")
	content.scrollTop +=9999;
}
