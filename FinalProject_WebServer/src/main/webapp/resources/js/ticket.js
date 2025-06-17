// 왼쪽 버튼
function arrow_right(){
	const korDay = ["일", "월", "화", "수", "목", "금", "토"];
	
	// .date-selector 내부의 day 클래스들을 가져온다
	const dayButtons = document.querySelectorAll(".date-selector .day");
	dayButtons.forEach(b => b.classList.remove("selected")); // 화살표 버튼을 누르면, 선택된 표시 모두 제거
	
	for(let i=1; i<dayButtons.length; ++i){
		const btn = dayButtons[i];
		const prevBtn = dayButtons[i-1];
		
		prevBtn.value = btn.value;
		prevBtn.dataset.date = btn.dataset.date;
	}
	// 마지막 날짜를 현재 첫번째 요소에서 7일 후로 설정한다
	const firstDateStr = dayButtons[0].dataset.date; // 
	let [year, month, date] = firstDateStr.split("-");
	const lastDate = new Date(year, month - 1, date); // JS는 month가 0부터 시작!
	lastDate.setDate(lastDate.getDate() + 6);
	const lastBtn = dayButtons[6];
	
	const y = lastDate.getFullYear();
	const m = String(lastDate.getMonth()+1).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
	const d = String(lastDate.getDate()).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
	const day = korDay[lastDate.getDay()];
	
	const formatted = `${m}/${d}(${day})`;
	
	lastBtn.value = formatted;
	lastBtn.dataset.date = `${y}-${m}-${d}`;
}

// 오른쪽 버튼
function arrow_left(){
	const korDay = ["일", "월", "화", "수", "목", "금", "토"];
	// .date-selector 내부의 day 클래스들을 가져온다
	const dayButtons = document.querySelectorAll(".date-selector .day");
	dayButtons.forEach(b => b.classList.remove("selected")); // 화살표 버튼을 누르면, 선택된 표시 모두 제거
	
	for(let i=dayButtons.length-1; i>0; --i){
		const btn = dayButtons[i];
		const prevBtn = dayButtons[i-1];
		
		btn.value = prevBtn.value;
		btn.dataset.date = prevBtn.dataset.date;
	}
	// 마지막 날짜를 현재 첫번째 요소에서 7일 후로 설정한다
	const lastDateStr = dayButtons[6].dataset.date; //
	let [year, month, date] = lastDateStr.split("-");
	const firstDate = new Date(year, month - 1, date); // JS는 month가 0부터 시작! 
	firstDate.setDate(firstDate.getDate()-6);
	const firstBtn = dayButtons[0];

	const y = firstDate.getFullYear();
	const m = String(firstDate.getMonth()+1).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
	const d = String(firstDate.getDate()).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
	const day = korDay[firstDate.getDay()];

	const formatted = `${m}/${d}(${day})`;

	firstBtn.value = formatted;
	firstBtn.dataset.date = `${y}-${m}-${d}`;
}

// 페이지가 처음 로드될 때, 오늘 날짜를 day1에 놓고 그 다음 day7까지 기본 날짜 버튼 세팅을 해주는 함수
function initDayButton(){
	const today = new Date();
	const korDay = ["일", "월", "화", "수", "목", "금", "토"];
	
	// .date-selector 내부의 day 클래스들을 가져온다
	const dayButtons = document.querySelectorAll(".date-selector .day");
	dayButtons.forEach((btn, i) => {
		const nextDate = new Date(today); // 오늘 날짜를 복사한다.
		nextDate.setDate(today.getDate() + i); // 오늘 날짜에 해당 반복문의 인덱스를 더해서, i일 뒤의 날짜를 결정한다.
		
		const year = nextDate.getFullYear();
		const month = String(nextDate.getMonth()+1).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
		const date = String(nextDate.getDate()).padStart(2, "0"); // 2글자로 범위를 정하고, 남은 부분은 padding을 앞에서부터 "0"으로 채운다.
		const day = korDay[nextDate.getDay()];
		
		const formatted = `${month}/${date}(${day})`; // -> "" 이거 말고 ``(백틱)으로 감싸야 함
				
		btn.value = formatted; 
		btn.dataset.date = `${year}-${month}-${date}`;
	});
	
	
}

// theaterSelect select 태그에 이벤트 리스너를 설정하는 함수
// 해당 태그 값이 바뀌면, 영화, 시간, 자리 태그의 값들을 모두 비운다.
function addEventListenerToTheaterSelect() {
  	const theaterSelect = document.getElementById("theaterSelect");
  	const movieSelect = document.getElementById("movieSelect");
  	const timeSelect = document.getElementById("timeSelect");
  	const rowInput = document.getElementById("row");
  	const colInput = document.getElementById("col");
	
	const hiddenTheaterId = document.getElementById("theater_id");
	const hiddenDate = document.getElementById("date");
	const hiddenMovieId = document.getElementById("movie_id");
	const hiddenTime = document.getElementById("time");

	theaterSelect.addEventListener("change", () => {
		// 영화 목록 초기화
		movieSelect.innerHTML = "<option>영화를 선택하세요</option>";
	
		// 날짜 목록 초기화
		initDayButton();
		
	    // 시간 목록 초기화
	    timeSelect.innerHTML = "<option>시간 선택</option>";
	
	    // 좌석 입력 초기화
	    rowInput.value = "";
	    colInput.value = "";
		
		// 숨겨진 데이터들 모두 초기화
		hiddenTheaterId.value = "";
		hiddenDate.value = "";
		hiddenMovieId.value = "";
		hiddenTime.value = "";
	});
}




// 날짜 버튼 이벤트 리스너 추가 함수
// 날짜 선택 시, theaterSelect select 태그의 option값에 있는 theater_id값과 해당 날짜값(data-date)을 가져와서 서버에게 보내준다.
function addEventListenerToDays() {
	const dayButtons = document.querySelectorAll(".date-selector .day");

	dayButtons.forEach(btn => {
		btn.addEventListener("click", () => {
			const date = btn.dataset.date;
			const theaterSelect = document.getElementById("theaterSelect");
			
			// theaterSelect 태그에 있는 값 중, selected된 인덱스의 값을 가지고 와서 해당 option의 value를 가져온다
			const theater_id = theaterSelect.options[theaterSelect.selectedIndex].value;
			
			
			const encodedTheaterId = encodeURIComponent(theater_id);
			const encodedDate = encodeURIComponent(date);
			
			// 현재 선택된 날짜 버튼에 selected 클래스 부여
		    dayButtons.forEach(b => b.classList.remove("selected")); // 모두 제거
			btn.classList.add("selected");
			
			// 2개의 hidden 태그에 value값을 지정함
			const hiddenDate = document.getElementById("date");
			hiddenDate.value = date;
			const hiddenTheaterId = document.getElementById("theater_id");
			hiddenTheaterId.value = theater_id;
			
			fetch(`TicketController?action=movie&theater_id=${encodedTheaterId}&date=${encodedDate}`, {
				method:"GET",
				headers: {
					"X-Requested-With": "XMLHttpRequest"
				}
			})
			.then(res => res.json())
				// res는 Response 객체. 서버로부터 받은 Response 객체의 body를 res.json() 함수로 json 형태로 바꾸는 것
				
				// "List<Map<String, Object>> movieList"" 가 반환됨. movie_id와 title을 담고 잇음 
			.then(data => { // data : 서버에서 응답받은 json 데이터. 해당 데이터를 다음 단계인 "movieSelect" select 태그에 option들을 추가해주는 함수에 전달한다.
				addOptionsToMovieSelect(data.movieList);
			});
		});
	});
}

// movieSelect select 태그에 option들을 추가해주는 함수
function addOptionsToMovieSelect(movieList){
	const movieSelect = document.getElementById("movieSelect");
	
	// movieSelect의 내부 innerHTML을 비워준다
	movieSelect.innerHTML = "<option>영화를 선택하세요</option>";
	// movieList에 담긴 값만큼 
	for(let i=0; i<movieList.length; ++i){
		// 새로운 option을 만들어서 value에는 movie_id, 안에 내용에는 영화 title을 지정해서 movieSelect의 자식에 추가해준다.
		const option = document.createElement("option");
		option.value = movieList[i].movie_id;
		option.textContent = movieList[i].title;
		movieSelect.appendChild(option);
	}
}


// movieSelect select 태그에 액션리스너를 추가하는 함수
function addEventListenerToMovieSelect(){
	// movieSelect id의 option 태그들을 전부 가져온다.
	const movieSelect = document.getElementById("movieSelect");
	
	// movieSelect 태그에게 "change"되었을 시 발생시킬 액션리스너를 설정한다. 
	movieSelect.addEventListener("change", ()=>{
		// 현재 movieSelect에 선택되어있는 값(movie_id)을 가져옴
		const movie_id = movieSelect.options[movieSelect.selectedIndex].value;
		// movie_id값 저장
		const hiddenMovieId = document.getElementById("movie_id");
		hiddenMovieId.value = movie_id;
		
		// theater_id 가져오기
		const theater_id = document.getElementById("theater_id").value;
		// date 가져오기
		const date = document.getElementById("date").value;
		
		
		const encodedMovieId = encodeURIComponent(movie_id);
		const encodedTheaterId = encodeURIComponent(theater_id);
		const encodedDate = encodeURIComponent(date);
		
		fetch(`TicketController?action=time&theater_id=${encodedTheaterId}&date=${encodedDate}&movie_id=${encodedMovieId}`, {
			method:"GET",
			headers:{
				"X-Requested-With": "XMLHttpRequest"
			}
		})
		.then(res=>res.json())
		.then(data =>{
			addOptionsToTimeSelect(data.timeList);
		});
	});	
}
// timeSelect 태그에 option들을 추가해주는 함수
function addOptionsToTimeSelect(timeList) {
	const timeSelect = document.getElementById("timeSelect");

	// 기존 옵션 초기화
	timeSelect.innerHTML = "<option>시간 선택</option>";

	// timeList는 ["10:00"...] 형태의 문자열 배열
	timeList.forEach(time => {
		const option = document.createElement("option");
		option.value = time;
		option.textContent = time;
		timeSelect.appendChild(option);
	});
}


// time을 선택했을 때 (select 태그에 change가 일어났을 때)의 이벤트 리스너를 해당 태그에 추가하는 함수
function addEventListenerToTimeSelect(){
	const timeSelect = document.getElementById("timeSelect");
	timeSelect.addEventListener("change", () =>{
		const hiddenTime = document.getElementById("time");
		hiddenTime.value = this.options[this.selecedIndex].value;
	});
}


// "예매하기" 버튼을 눌렀을 때 실행되는 함수.
// 모든 데이터들이 설정되어있는 지 확인하고, 만약 데이터들이 설정되어있지 않은 상태라면 예매가 안되도록 설정한다  
function ticketFunc(){
	// 예메에 필요한 모든 데이터가 있는지 확인한다
	const theater_id = document.getElementById("theater_id");
	const date = document.getElementById("date");
	const movie_id = document.getElementById("movie_id");
	const time = document.getElementById("time");
	const row = document.getElementById("row").value;
	const col = document.getElementById("col").value;
	
	// 필수 값들이 비어있을 경우 경고
	if (!theater_id) {
	  alert("극장을 선택해주세요.");
	  return false;
	}
	if (!date) {
	  alert("날짜를 선택해주세요.");
	  return false;
	}
	if (!movie_id) {
	  alert("영화를 선택해주세요.");
	  return false;
	}
	if (!time) {
	  alert("시간을 선택해주세요.");
	  return false;
	}
	if (!row || !col) {
	  alert("좌석의 행/열을 입력해주세요.");
	  return false;
	}

	// 모든 값이 채워져 있으면 폼 제출
	return true;	
}


window.onload = function(){
	initDayButton();
	addEventListenerToTheaterSelect(); // theaterSelect에 이벤트 리스너 추가
	addEventListenerToDays(); // 날짜 버튼에 이벤트 리스너 추가
	addEventListenerToMovieSelect(); // 영화 선택 태그에 이벤트 리스너 추가
	addEventListenerToTimeSelect(); // 시간 선택 태그에 이벤트 리스너 추가
	

	const firstBtn = document.querySelector(".theater-tabs button");
	if (firstBtn) firstBtn.click();

}

