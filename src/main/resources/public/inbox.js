
function getSlNoRow(currIdx, msg) {
	return $strRow = "<th scope='row'><div>"+ (currIdx + 1)+"</div><div style='display:none'>"+msg.messageId+"</div></th>";
}

function getFromRow(msg) {
	return $strRow = "<td><div>"+ msg.mobileNumber + "</div></td>";
}

function getMessageRow(msg) {
	return $strRow = "<td><div>"+msg.message+"</div></td>";
}

function getDateRow(msg) {
	return $strRow = "<td><div>"+msg.messageDate+"</div></td>";
}

function getBlankRow() {
	return $strRow = "<tr> <td colspan='4'><div align='center'><br/><i>No messages</i></div></td></tr>";
}


$('#search-input').keypress(function(e) {
    if (e.which == '13') {
        getMessageList(this.value);
    }
});

/*
$("#search-mobile").on("click", function(){
	var mobileNumber = $("#search-input").val();
	alert('fetching:  '  + mobileNumber );
	var msgList = getMessageList(mobileNumber);
	
});
*/
function getMessageList(mobileNumber) {
	 
		//var url = "http://14.96.102.88:20164/serviceRequest/GET_NOTIFICATION?mobileNumber=" + mobileNumber;
		//var url = "http://localhost:20164/serviceRequest/GET_NOTIFICATION?mobileNumber=" + mobileNumber;
        var url = "serviceRequest/GET_NOTIFICATION?mobileNumber=" + mobileNumber;

		getDataFromServer(url, function(data) {
		
			var msgLst = data.messageList;
			var str = "";
			if(msgLst.length == 0) {
				str = getBlankRow();
				$('#message-table').html(str);
				return;
			}
			
			for(i =0; i < msgLst.length; i++) {
				str += "<tr>";
				str += getSlNoRow(i, msgLst[i]);
				str += getFromRow(msgLst[i]);
				str += getMessageRow(msgLst[i]);
				str += getDateRow(msgLst[i]);
				str += "</tr>";	
			}
			
			
			$('#message-table').html(str);
		});
		
}

function getDataFromServer(url, callback) {
			
			$.ajax({
				url: url, 
				async : false,
				success: function(data, status, xhr){
					callback(data);
				}
			});
}			


