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

// day 버튼들을 누르면 해당 값이 서버에게 넘어가서 날짜별 영화 상영 데이터들을 새롭게 가져와 화면을 다시 보이게 하도록 하는 
// actionListener를 할당해주는 함수
function addActionListenerToDays() {
	const dayButtons = document.querySelectorAll(".date-selector .day");

	dayButtons.forEach(btn => {
		btn.addEventListener("click", () => {
			const screeningDate = btn.dataset.date;
			const theaterName = document.getElementById("selectedTheaterName").value;

			const encodedTheaterName = encodeURIComponent(theaterName);
			const encodedScreeningDate = encodeURIComponent(screeningDate);
			
			// 현재 선택된 날짜 버튼에 selected 클래스 부여
		    dayButtons.forEach(b => b.classList.remove("selected")); // 모두 제거
			btn.classList.add("selected");
			
			fetch(`TheaterController?theaterName=${encodedTheaterName}&screeningDate=${encodedScreeningDate}`, {
				method:"GET",
				headers: {
					"X-Requested-With": "XMLHttpRequest"
				}
			})
			.then(res => {
				if (!res.ok) throw new Error("서버 응답 오류: " + res.status);
				    return res.json();
			}) // res는 Response 객체. 서버로부터 받은 Response 객체의 body를 res.json() 함수로 json 형태로 바꾸는 것 
			.then(data => { // data : 서버에서 응답받은 json 데이터. 해당 데이터를 printMovieInfoList에 전달한다.
				printMovieInfoList(data.movieInfoList);
			});
		});
	});
}

// "movieInfoContainer" div에 영화 정보 리스트를 출력하는 함수
function printMovieInfoList(movieInfoList) {
	const container = document.querySelector(".movieInfoContainer");
	container.innerHTML = ""; // 기존 내용 지우기

	if (!movieInfoList || movieInfoList.length === 0) {
		container.innerHTML = "<p>상영 중인 영화가 없습니다.</p>";
		return;
	}

	movieInfoList.forEach(movie => {
		// 상영 정보가 없으면 패스
		if (!movie.screeningInfoList || movie.screeningInfoList.length === 0) return;

		const movieCard = document.createElement("div");
		movieCard.className = "movie-card";
		movieCard.innerHTML = `<h3>${movie.title}</h3><p>${movie.genre} | ${movie.duration}분 | 개봉일: ${movie.target_date}</p>`;

		movie.screeningInfoList.forEach(screeningInfo => {
			const info = document.createElement("div");
			info.className = "screen-info";
			info.innerHTML = `
				<div>${screeningInfo.room_number}관</div>
				<a class="time-box" href="#">
					<span class="time-text">${screeningInfo.start_time}</span><br>
				 	<span class="seat-count">${screeningInfo.remainSeat}</span>
				</a>`;
			movieCard.appendChild(info);
		});

		container.appendChild(movieCard);
	});
}



// 영화관 리스트 버튼에 액션 리스너 추가하기
function addActionListenerToTheaters() {
  const buttons = document.querySelectorAll(".theater-tabs button");
  const hiddenInput = document.getElementById("selectedTheaterName");

	buttons.forEach(btn => {
	btn.addEventListener("click", () => {
		const theaterName = btn.textContent.trim(); // 버튼에 표시된 극장 이름
		hiddenInput.value = theaterName;            // hidden input에 저장
		
		// 기존 선택 해제
	    buttons.forEach(b => b.classList.remove("selected"));
	    // 선택된 버튼에 적용
	    btn.classList.add("selected");
		
		
		const today = new Date();
		const year = today.getFullYear();
		const month = String(today.getMonth()+1).padStart(2, "0"); 
		const date = String(today.getDate()).padStart(2, "0"); 
		const screeningDate = `${year}-${month}-${date}`;
		
		const encodedTheaterName = encodeURIComponent(theaterName);
		const encodedScreeningDate = encodeURIComponent(screeningDate);
		fetch(`TheaterController?theaterName=${encodedTheaterName}&screeningDate=${encodedScreeningDate}`,{
			method:"GET",
			headers: {
	        	"X-Requested-With": "XMLHttpRequest"
			}
		})
		.then(res => res.json())
		.then(data => {
			printMovieInfoList(data.movieInfoList); // 해당 극장과 날짜 데이터를 똑같이 적용해서, 그에 맞는 영화 데이터들을 이용해서 출력함
		});
    });
  });
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



window.onload = function(){
	initDayButton();
	addActionListenerToTheaters();
	addActionListenerToDays();

	const firstBtn = document.querySelector(".theater-tabs button");
	if (firstBtn) firstBtn.click();

}


