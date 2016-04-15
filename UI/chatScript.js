'use strict';
var username = "";
var Application = {
    mainUrl : 'http://localhost:8080/chat',
    messageList : [],
    token : 'TN11EN',
    isConnected : null
};
function run(){
	var appContainer = document.getElementsByClassName('all')[0];
	appContainer.addEventListener('click', delegateMessage);
	appContainer.addEventListener('keydown', delegateMessage);
	
	username = loadUsername() || "Anonymous";
	var input = document.getElementById('nameInput');
	input.value = username;
	var currentUser = document.getElementById('current');
   	currentUser.innerHTML = username;
   	
   	loadHistory() 
   	fixScroll();		
}
function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random).toString();;
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
	divItem.setAttribute('id', 'divId' + message.id);
	text.setAttribute('id', 'textDiv' + message.id);
	delStatus.setAttribute('id', 'textDel' + message.id);
	editStatus.setAttribute('id', 'textEdit' + message.id);
	input.setAttribute('id', 'textIn' + message.id);
	input.setAttribute('class', 'In');
	btnDel.setAttribute('id','del' + message.id);
    btnEdit.setAttribute('id','red' + message.id);
    btnCancel.setAttribute('id','cancel' + message.id);
    
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
  		if(message.author != username){
  			btnDel.style.display = 'none';	
   			btnEdit.style.display = 'none';
  		}
  	}
	
	textName.innerHTML = message.author;
	time.innerHTML = new Date(message.timestamp).toLocaleString();
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
    updateHistory(Application.messageList);
}

function sendMessage() {
	var inputText = document.getElementById('fieldMessage');
	var str = inputText.value;
	if(str != "") {
		var newMes = newMessage(str);
    	Application.messageList.push(newMes);
    	saveMessage(newMes);
    	inputText.value = "";
    }
	fixScroll();	
}
function saveMessage(newMessage) {
    ajax('POST', Application.mainUrl, JSON.stringify(newMessage), function(){
        updateHistory(Application.messageList);
    });
}

function deleteMessage(message) {
	var thisMes = {
        id: message.id
    };
	ajax('DELETE', Application.mainUrl, JSON.stringify(thisMes), function(){
        message.deleted = true;
        message.text = "";
        updateHistory(Application.messageList);
    });
}

function changeMessage(message) {
	
	var input = document.getElementById('textIn' + message.id);
	var btnCancel = document.getElementById('cancel'+message.id);

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
				updateHistory(Application.messageList);
				
			}
			input.hidden = true;
			btnCancel.style.display = 'none';
				
		}
	});		
}
function cancelEditMessage(message){
  	var k = document.getElementById('divId' + message.id);
	var text = document.getElementById('textDiv' + message.id);
	var input = document.getElementById('textIn' + message.id);
	var btnCancel = document.getElementById('cancel'+ message.id);
			input.hidden = true;
			btnCancel.style.display = 'none';

}
function newMessage(text) {

    return {
        author: username,
        id: uniqueId(),
        text: text,
        timestamp: new Date().getTime(),
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
    var url = Application.mainUrl + '?token=' + Application.token;

    ajax('GET', url, null, function(responseText){
        var json = JSON.parse(responseText);
        Application.messageList = json.messages;
        updateHistory(Application.messageList);
    });
    if (Application.messageList == null) {
        Application.messageList = [];
    }
}


function updateHistory(messageHistory) {
	document.getElementById('list').innerHTML = "";
    for (var i = 0; i < messageHistory.length; i++) {
    	showHistory(messageHistory[i]);
    }
}
function fixScroll(){
	var content = document.getElementById("chat")
	content.scrollTop +=9999;
}

function ajax(method, url, data, continueWith) {
        var xhr = new XMLHttpRequest();

        xhr.open(method || 'GET', url, true);

        xhr.onload = function () {
            if (xhr.readyState !== 4)
                return;

            if(xhr.status != 200) {
                defaultErrorHandler('Error on the server side, response ' + xhr.status);
                return;
            }

            if(isError(xhr.responseText)) {
                defaultErrorHandler('Error on the server side, response ' + xhr.responseText);
                return;
            }

            continueWith(xhr.responseText);
            Application.isConnected = true;
        };

        xhr.ontimeout = function () {
            ServerError();
        };

        xhr.onerror = function () {
            ServerError();
        };

        xhr.send(data);
    }
function isError(text) {
    if(text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch(ex) {
        return true;
    }

    return !!obj.error;
}    