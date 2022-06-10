$().ready(function () {
  
    $("#checkemail").click(function () {
      Swal.fire({
        icon: 'success',
        title: '비밀번호 찾기',
        text: '가입하신 이메일을 확인해주세요.',
      });
    });
    
   $("#delete").click(function () {
      Swal.fire({
        title: '이 글을 삭제하시겠습니까?',
        text: "삭제하시면 다시 복구시킬 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
        reverseButtons: true, // 버튼 순서 거꾸로
        
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = "./page.html";
        } 
      })
   }); 
  
  $("#delete_admin").click(function () {
      Swal.fire({
        title: '이 글을 삭제하시겠습니까?',
        text: "삭제하시면 다시 복구시킬 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
        reverseButtons: true, // 버튼 순서 거꾸로
        
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = "./noticeboard_admin.html";
        } 
      })
    }); 
  
  
    $("#result").click(function () {
      Swal.fire({
        title: '검색 결과가 없습니다.',
        text: "직접 스터디를 개설하시겠습니까?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '개설하기',
        cancelButtonText: '아니오',
        reverseButtons: true, // 버튼 순서 거꾸로
        
      }).then((result) => {
        if (result.isConfirmed) {
         location.href = "groups/new.html";
        } 
      })
    });
    
    
    $("#promptStart").click(function () {
      
      (async () => {
          const { value: getName } = await Swal.fire({
              title: '당신의 이름을 입력하세요.',
              text: '그냥 예시일 뿐이니 정보유출 같은건 없습니다.',
              input: 'text',
              inputPlaceholder: '이름을 입력..'
          })
  
          // 이후 처리되는 내용.
          if (getName) {
              Swal.fire(`: ${getName}`)
          }
      })()
    });
    
    
    $("#toastStart").click(function () {
      const Toast = Swal.mixin({
        toast: true,
        position: 'center-center',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      })
  
      Toast.fire({
        icon: 'success',
        title: 'toast 알림이 정상적으로 실행 되었습니다.'
      })
    });
    
    
    $("#ajaxStart").click(function () { 
      Swal.fire({
        title: 'Submit your Github username',
        input: 'text',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Look up',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
          return fetch(`//api.github.com/users/${login}`)
            .then(response => {
              if (!response.ok) {
                throw new Error(response.statusText)
              }
              return response.json()
            })
            .catch(error => {
              Swal.showValidationMessage(
                `Request failed: ${error}`
              )
            })
        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: `${result.value.login}'s avatar`,
            imageUrl: result.value.avatar_url
          })
        }
      })
    });
    
  });