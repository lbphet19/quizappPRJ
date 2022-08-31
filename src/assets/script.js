// export function sliderr(direction,index){
// 	var box = document.querySelector('#my-scroll-item-'+index);
//     if(direction == 'left'){
//             box.scrollLeft -= 500;
//         } else {
//             box.scrollLeft += 500;
//         }
// }

var sliderr = (function() {

  return{
    slide : function(direction,index){
    	var box = document.querySelector('#my-scroll-item-'+index);
        if(direction == 'left'){
                box.scrollLeft -= 500;
            } else {
                box.scrollLeft += 500;
            }
    }
  }
})(sliderr||{})
