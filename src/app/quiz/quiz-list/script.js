export function sliderr(direction,index){
	var box = document.querySelector('#my-scroll-item-'+index);

	// const scrollbarWidth = box.offsetWidth;
	// const clientWidth = box.clientWidth;
	// // alert(scrollbarWidth);
 //    var container = document.getElementById('first-scroll-item');
    // scrollCompleted = 0;
    // var slideVar = setInterval(function(){
    //     if(direction == 'left'){
    //         box.scrollLeft -= 200;
    //     } else {
    //         box.scrollLeft += 200;
    //     }
    //     scrollCompleted += 200;
    //     if(scrollCompleted >= 200){
    //         window.clearInterval(slideVar);
    //     }
    // }, 0);
    if(direction == 'left'){
            box.scrollLeft -= 500;
        } else {
            box.scrollLeft += 500;
        }
}
