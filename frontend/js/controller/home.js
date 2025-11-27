const URL = "http://localhost:3000/api/v1/home";

export async function fetchHomeData() {
    try {
        const response = await fetch(URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        return data;
        
    } catch (error) {
        console.error("Error fetching home data:", error);
        throw error;
    }
}