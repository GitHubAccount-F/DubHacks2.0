import React, { useEffect, useMemo, useState } from "react";
import Papa from "papaparse";

// -----------------------------------------
// Constants & helpers
// -----------------------------------------

const PLACEHOLDER_POSTER = "https://placehold.co/200x300?text=No+Poster";
const TMDB_BASE = "https://image.tmdb.org/t/p/w342";

const safe = (x) => (x == null ? "" : String(x));

function buildPosterUrl(value) {
  const s = safe(value).trim();
  if (!s) return "";
  if (s.startsWith("http")) return s;         // full URL already
  if (s.startsWith("/")) return TMDB_BASE + s; // TMDB path
  return s;                                    // unknown format, try as-is
}

// Split genres regardless of format (JSON array / Python-like / delimited)
function normalizeGenres(value) {
  const raw = (value ?? "").toString().trim();
  if (!raw) return [];

  // Pull out 'name' fields from Python/JSON-ish objects
  const nameMatches = [...raw.matchAll(/['"]name['"]\s*:\s*['"]([^'"]+)['"]/g)].map((m) => m[1]);
  if (nameMatches.length) {
    return nameMatches.filter(Boolean).map((s) => s.trim());
  }

  // Try to coerce single quotes to JSON and fix object separators
  if (raw.startsWith("[") && raw.endsWith("]")) {
    let candidate = raw.replace(/'/g, '"').replace(/}\s*{/g, "},{");
    try {
      const parsed = JSON.parse(candidate);
      if (Array.isArray(parsed)) {
        return parsed
          .map((g) => (typeof g === "string" ? g : g?.name))
          .filter(Boolean)
          .map((s) => s.trim());
      }
    } catch (_) { /* fall through */ }
  }

  // Fallback: split on common delimiters
  return raw.split(/[|,;/]/).map((s) => s.trim()).filter(Boolean);
}

// Genre → color
const GENRE_COLORS = {
  Action: "#f87171",
  Adventure: "#60a5fa",
  Animation: "#fb923c",
  Comedy: "#facc15",
  Crime: "#f97316",
  Drama: "#34d399",
  Fantasy: "#a78bfa",
  Horror: "#ef4444",
  Mystery: "#c084fc",
  Romance: "#f472b6",
  SciFi: "#38bdf8",
  "Sci-Fi": "#38bdf8",
  Thriller: "#22d3ee",
  War: "#94a3b8",
  Western: "#f59e0b",
};
const genreColor = (name) => GENRE_COLORS[(name || "").trim().replace(/\s+/g, "")] || "#1e293b";

// -----------------------------------------
// Component
// -----------------------------------------

export default function MoodFlixApp({ csvUrl = "/movies_final_set.csv" }) {
  const [rows, setRows] = useState([]);
  const [query, setQuery] = useState("");
  const [page, setPage] = useState(1);
  const [flippedId, setFlippedId] = useState(null); // ✅ moved INSIDE the component
  const pageSize = 10;

  // Load CSV
  useEffect(() => {
    Papa.parse(csvUrl, {
      download: true,
      header: true,
      dynamicTyping: true,
      skipEmptyLines: true,
      complete: ({ data }) => setRows((data || []).filter(Boolean)),
      error: (e) => console.error("CSV load error:", e),
    });
  }, [csvUrl]);

  // Prefer your columns; fall back to guesses
  const cols = useMemo(() => {
    const sample = rows[0] || {};
    const has = (k) => Object.prototype.hasOwnProperty.call(sample, k);
    return {
      id: ["id", "movieId", "tmdb_id", "imdb_id"].find(has) || "id",
      title: ["title", "original_title", "name", "movie_title"].find(has) || "title",
      genres: ["genres", "genre"].find(has) || "genres",
      rating: ["rating_tmdb", "vote_average", "rating", "score"].find(has) || "rating_tmdb",
      poster: ["poster_path", "poster", "image", "poster_url"].find(has) || "poster_path",
      // for search:
      overview: ["overview", "description", "plot", "tagline"].find(has) || "overview",
      keywords: ["keywords", "all_keywords", "tags"].find(has) || "keywords",
      date: ["release_date", "year", "release_year", "date"].find(has) || "release_date",
    };
  }, [rows]);

  // Search filter
  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase();
    if (!q) return rows;
    return rows.filter((r) => {
      const t = safe(r[cols.title]).toLowerCase();
      const o = safe(r[cols.overview]).toLowerCase();
      const g = safe(r[cols.genres]).toLowerCase();
      const k = safe(r[cols.keywords]).toLowerCase();
      return t.includes(q) || o.includes(q) || g.includes(q) || k.includes(q);
    });
  }, [rows, query, cols]);

  // Pagination
  const totalPages = Math.max(1, Math.ceil(filtered.length / pageSize));
  const current = Math.min(page, totalPages);
  const start = (current - 1) * pageSize;
  const pageRows = filtered.slice(start, start + pageSize);

  // Reset page on new search
  useEffect(() => setPage(1), [query]);

  return (
    <div className="mf-wrap">
      {/* Fixed top bar */}
      <header className="mf-topbar">
        <div className="mf-topbar-inner">
          <div className="mf-brand">🎬 MovieFinder</div>
          <input
            className="mf-search"
            placeholder="Search title, description, genre, or keyword…"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
        </div>
      </header>

      <main className="mf-container">
        <div className="mf-results-count">
          Showing {filtered.length === 0 ? 0 : start + 1}–{Math.min(filtered.length, start + pageSize)} of {filtered.length}
        </div>

        {/* Grid */}
        <ul className="mf-grid">
          {pageRows.map((r, i) => {
            const id = safe(r[cols.id]) || String(i);
            const title = safe(r[cols.title]) || "Untitled";
            const ratingVal = parseFloat(r[cols.rating]);
            const ratingText = Number.isFinite(ratingVal) ? ratingVal.toFixed(1) : "N/A";
            const genres = normalizeGenres(r[cols.genres]);
            const poster = buildPosterUrl(r[cols.poster]) || PLACEHOLDER_POSTER;
            const overview = safe(r[cols.overview]);
            const isFlipped = flippedId === id;

            const toggleFlip = () => setFlippedId(isFlipped ? null : id);

            return (
              <li key={id} className={`mf-card mf-card3d ${isFlipped ? "is-flipped" : ""}`}>
                <div className="mf-card-inner">
                  {/* FRONT */}
                  <div className="mf-card-face mf-card-front">
                    <button
                      className="mf-flip-btn"
                      onClick={toggleFlip}
                      aria-pressed={isFlipped}
                      aria-label="Show overview"
                      title="Show overview"
                    >
                      ⓘ
                    </button>

                    <div className="mf-poster-wrap">
                      <img
                        className="mf-poster"
                        src={poster}
                        alt={title}
                        loading="lazy"
                        onError={(e) => { e.currentTarget.src = PLACEHOLDER_POSTER; }}
                      />
                    </div>

                    <div className="mf-card-body">
                      <div className="mf-title">{title}</div>
                      <div className="mf-meta">
                        <span className="mf-star">⭐</span>
                        <strong>{ratingText}</strong>
                        <span className="mf-outof"> / 10</span>
                      </div>

                      {genres.length > 0 && (
                        <div className="mf-tags">
                          {genres.slice(0, 3).map((g, idx) => (
                            <span
                              key={idx}
                              className="mf-tag"
                              style={{
                                backgroundColor: genreColor(g),
                                color: "#0b1224",
                                border: "none",
                                fontWeight: 600,
                              }}
                            >
                              {g}
                            </span>
                          ))}
                          {genres.length > 3 && (
                            <span className="mf-tag mf-tag--more">+{genres.length - 3}</span>
                          )}
                        </div>
                      )}
                    </div>
                  </div>

                  {/* BACK */}
                  <div className="mf-card-face mf-card-back">
                    <button
                      className="mf-flip-btn mf-flip-btn--back"
                      onClick={toggleFlip}
                      aria-pressed={isFlipped}
                      aria-label="Show poster"
                      title="Show poster"
                    >
                      ←
                    </button>

                    <div className="mf-back-body">
                      <div className="mf-back-title">{title}</div>
                      <div className="mf-back-meta">
                        <span className="mf-star">⭐</span>
                        <strong>{ratingText}</strong>
                        <span className="mf-outof"> / 10</span>
                      </div>
                      <div className="mf-overview" title={overview}>
                        {overview || "No overview available."}
                      </div>
                    </div>
                  </div>
                </div>
              </li>
            );
          })}
        </ul>

        {/* Pagination */}
        <nav className="mf-pager" aria-label="Pagination">
          <button disabled={current === 1} onClick={() => setPage((p) => Math.max(1, p - 1))}>‹ Prev</button>
          {Array.from({ length: totalPages }, (_, i) => i + 1)
            .slice(Math.max(0, current - 4), Math.max(0, current - 4) + 7)
            .map((p) => (
              <button
                key={p}
                className={p === current ? "is-active" : ""}
                onClick={() => { setPage(p); window.scrollTo({ top: 0, behavior: "smooth" }); }}
              >
                {p}
              </button>
            ))}
          <button disabled={current === totalPages} onClick={() => setPage((p) => Math.min(totalPages, p + 1))}>Next ›</button>
        </nav>
      </main>
    </div>
  );
}