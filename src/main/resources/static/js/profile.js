//프로필 이미지 수정
$(".profile-upload").click(function(){
    const data = {
        id : $("#userId").val(),
        file : $("#inputGroupFile04").val()
    }

    const form = $("#inputGroupFile04")[0].files[0];
    const formData = new FormData();

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
            enctype : 'multipart/form-data',
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        }).done(function (response) {
            let url = response.path;
            window.location.reload();
        }).fail(function (error) {
            alert("사진 업로드에 실패했습니다. 다시 시도해주세요.");
        });
    }
});
