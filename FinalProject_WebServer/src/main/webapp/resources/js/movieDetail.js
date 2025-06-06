console.log("js 파일 실행됨");


function set_rating(){
	const stars = document.querySelectorAll(".rating_stars span")
	
	
	
	stars.forEach(star => {
		// 각 star span 별로 click되면 데이터가 rating name 태그에 저장되도록 설정 
		star.addEventListener("click", () =>{
			const rating = star.getAttribute("data-value");
			document.getElementById("review_rating").value=rating;
			
			// 모든 별 초기화
		stars.forEach(s => s.classList.remove("selected"));

		// 클릭한 별까지만 색칠
		const value = Number(star.getAttribute("data-value"));
		for(let i=0; i<value; ++i){
			stars[i].classList.add("selected")
		}
		
		
		});
	});
	
	stars.forEach(star => {
			// 각 star span 별로 click되면 데이터가 rating name 태그에 저장되도록 설정 
			star.addEventListener("hover", () =>{
				const rating = star.getAttribute("data-value");
				document.getElementById("review_rating").value=rating;
				
				// 모든 별 초기화
			stars.forEach(s => s.classList.remove("selected"));

			// 클릭한 별까지만 색칠
			const value = Number(star.getAttribute("data-value"));
			for(let i=0; i<value; ++i){
				stars[i].classList.add("selected")
			}
			});
		});
}


window.onload = function(){
	set_rating();	
}
