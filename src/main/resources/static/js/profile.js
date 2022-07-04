//프로필 이미지 수정
$(".profile-upload").click(function(){
    let data = {
        id : $("#userId").val(),
        file : $("#inputGroupFile04").val()
    }

    let form = $("#inputGroupFile04")[0].files[0];
    let formData = new FormData();

    formData.append('file',form);

    extension = data.file.slice(data.file.indexOf(".")+1).toLowerCase();

    if (!data.file) {
        alert("사진을 선택해주세요.");
        return false;
    } else if(extension != "jpg" && extension != "png") {
        alert("jpg나 png 파일만 첨부 가능합니다.");
        return false;
    } else {
        $.ajax({
            type : "POST",
            data : formData,
            url : "/profile/"+data.id,
            cache : false,
            contentType : false,
            processData : false,
            enctype : 'multipart/form-data'
        }).done(function (response) {
            let url = response.path;
            window.location.reload();
        }).fail(function (error) {
            alert("실패");
            alert(JSON.stringify(error));
        });
    }
});
