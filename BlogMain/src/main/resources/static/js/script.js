console.log("This is script se");

const toggleSidebar = () => {
    if($(".sidebar").is(":visible")){
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left","0%");
    }
    else{
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left","20%");
    }
};

const toggleComment = () => {
    if($(".comment").is(":visible")){
        $(".comment").css("display", "none");
        $(".desc").css("margin-bottom","0%");
    }
    else{
        $(".comment").css("display", "block");
        $(".desc").css("margin-bottom","10%");
    }
};

function sendPutRequest(bId, event) {
    // event.preventDefault(); // Prevent the default action of the link
        fetch(`/user/blogger/${bId}/like`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            // // Add body if needed
            // body: JSON.stringify({
            //     // Your data here, if needed
            // })
        })
        .then(response => {
            if (response.ok) {
                alert('Blog Liked!');
            } else {
                alert('Error!');
            }
        })
        .catch(error => console.error('Error:', error));
    }
