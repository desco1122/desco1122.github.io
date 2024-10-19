//Daniel Escobedo - Artifact 1 - SNHU CS 499

// Wait for the DOM to be fully loaded before executing the script
document.addEventListener('DOMContentLoaded', (event) => {
    // Initialize variables
    let currentSlide = 0;
    const slides = document.querySelectorAll('.slide');
    const slidesContainer = document.querySelector('.slides');
    const totalSlides = slides.length;

    // Function to display a specific slide
    function showSlide(n) {
        // Ensure the slide index wraps around
        currentSlide = (n + totalSlides) % totalSlides;
        // Move the slides container to show the current slide
        slidesContainer.style.transform = `translateX(-${currentSlide * 100}%)`;
    }

    // Function to change to the next or previous slide
    function changeSlide(n) {
        showSlide(currentSlide + n);
    }

    // Function to update the like/dislike count
    function updateCount(button, increment) {
        const countSpan = button.querySelector('span');
        let count = parseInt(countSpan.textContent);
        count += increment;
        countSpan.textContent = count;
    }

    // Function to handle like/dislike button clicks
    function handleLikeDislike(event) {
        const button = event.target.closest('button');
        if (!button) return;

        if (button.classList.contains('like-btn')) {
            updateCount(button, 1);
        } else if (button.classList.contains('dislike-btn')) {
            updateCount(button, 1);
        }
    }

    // Function to reset all like/dislike counts
    function resetCounts() {
        document.querySelectorAll('.like-count, .dislike-count').forEach(countSpan => {
            countSpan.textContent = '0';
        });
    }

    // Add event listeners to navigation buttons
    document.querySelector('.prev').addEventListener('click', () => changeSlide(-1));
    document.querySelector('.next').addEventListener('click', () => changeSlide(1));

    // Add event listeners to like/dislike buttons
    document.querySelectorAll('.like-dislike-container').forEach(container => {
        container.addEventListener('click', handleLikeDislike);
    });

    // Add event listener to reset button
    document.querySelector('.reset-btn').addEventListener('click', resetCounts);

    // Initialize the slideshow
    showSlide(0);
});