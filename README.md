# Movie Finder  
*A mood-based movie discovery app designed to make choosing what to watch simple and enjoyable.*

---

## Overview  
**Movie Finder** is a web application that helps users discover movies by **mood, keyword, genre, or title**.  
Instead of endlessly scrolling through streaming platforms, users can explore films that match how they feel—whether they want something cozy, suspenseful, or inspiring.  

Built during **DubHacks 2025**, the project focuses on turning indecision into discovery through a calm, cinematic, and user-centered interface.

---

## Features  
- Search by keyword, genre, or title  
- Combine multiple genres for precise results  
- Flipping movie cards that reveal summaries, ratings, and genre tags  
- Integrated CSV dataset for fast local search  
- Pagination to view 10 movies per page  
- A cohesive “Lantern” dark theme with *Nanum Pen Script* and *Inter* fonts  

---

## Tech Stack  

| Category | Tools |
|-----------|-------|
| Frontend | React + Vite |
| Data Parsing | PapaParse |
| Styling | Custom CSS |
| Data Source | Pre-cleaned CSV (`movies_final_set.csv`) |
| Caching | LocalStorage |
| Deployment | Vercel / Netlify |

---

## Installation & Setup  

1. **Clone the repository**  
   ```bash
   git clone https://github.com/<your-username>/movie-finder.git
   cd movie-finder

2. Install dependencies
   ```bash
   npm install

3. Run the app
   ```bash
   npm run dev

4. Open your browser at

http://localhost:5173

## Data

Movie Finder uses a preprocessed dataset (movies_final_set.csv) containing thousands of movie records with attributes such as title, genre, overview, and rating.

We parsed this data locally using PapaParse, which allowed for fast, client-side filtering and search.
The dataset was cleaned to ensure consistency across columns (e.g., separating multiple genres, normalizing rating formats).

## Architecture
movie-finder/
 ├── public/
 │    └── movie-placeholder.png
 ├── src/
 │    ├── App.jsx
 │    ├── components/
 │    │    └── MovieCard.jsx
 │    ├── styles/
 │    │    └── index.css
 │    └── data/
 │         └── movies_final_set.csv
 ├── package.json
 └── README.md


- Frontend: Built in React + Vite for fast development and modular design
- Data Parsing: PapaParse parses the CSV and filters results dynamically
- Pagination: 10 movies displayed per page with navigation buttons
- UI: Responsive grid layout and flip-card animation for interaction

## Challenges

- Cleaning and structuring inconsistent CSV data
- Handling long movie descriptions and multi-genre formatting
- Building a responsive grid and pagination that scaled across screen sizes
- esigning a cohesive, cinematic interface that balanced clarity and atmosphere

## Accomplishments

- Built a working, visually consistent movie discovery tool
- Created a reliable local filtering system using real-world data
- Designed a clean and inviting interface inspired by movie-night aesthetics
- Learned to handle data manipulation entirely on the client side

## What We Learned

- How to effectively parse and normalize large datasets for frontend use
- The value of good data structure when building client-based search tools
- Techniques for improving user experience through color, typography, and animation
- Stronger teamwork, version control, and design collaboration skills

## Future Roadmap

- Add an AI chatbot to help users describe what they’re “in the mood for”
- Incorporate an AI-powered recommendation system based on user preferences
- Include more datasets (TV shows, documentaries, short films)
- Enable user-created playlists or “mood boards” for film curation

## Team

Czarin Dela Cruz – Frontend, UI/UX Design, Data Cleaning
Fardeen Azizi – Data Processing, Backend

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments

- The DubHacks 2025 organizing team
- Open-source data providers for access to public movie datasets
- Everyone who inspired the idea of simplifying indecision through design