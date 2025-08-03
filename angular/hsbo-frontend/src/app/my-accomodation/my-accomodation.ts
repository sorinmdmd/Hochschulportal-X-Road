import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, DatePipe } from '@angular/common'; // Import CommonModule and DatePipe
import { FormsModule } from '@angular/forms'; // Import FormsModule if needed for two-way binding, though not directly used here
// Define interfaces for the data structure
interface HousingUnit {
  id: string;
  name: string;
  description: string;
  category: string;
  city: string;
  zipCode: string;
  address: string;
  freeSpaces: number;
}

interface Accommodation {
  id: string;
  studentId: string;
  startDate: string; // ISO 8601 string
  expiryDate: string | null; // ISO 8601 string or null
  housingUnit: HousingUnit;
}

@Component({
  selector: 'app-my-accomodation',
  standalone: false,
  templateUrl: './my-accomodation.html',
  styleUrl: './my-accomodation.css'
})
export class MyAccomodation {
// Property to hold the fetched accommodation data
accommodation: Accommodation | null = null;
// Property to handle loading state
isLoading: boolean = true;
// Property to handle error messages
errorMessage: string | null = null;

// Inject HttpClient in the constructor
constructor(private http: HttpClient) {}

// ngOnInit is called after Angular initializes the component's data-bound properties
ngOnInit(): void {
  this.fetchAccommodation();
}

/**
 * Fetches accommodation data from the API.
 */
fetchAccommodation(): void {
  this.isLoading = true; // Set loading to true before the request
  this.errorMessage = null; // Clear any previous error messages
  const apiUrl = 'http://localhost:8085/api/x-road/housingUnit/getMyBooking';

  this.http.get<Accommodation>(apiUrl).subscribe({
    next: (data) => {
      this.accommodation = data;
      this.isLoading = false; // Set loading to false on success
      console.log('Accommodation data fetched:', this.accommodation);
    },
    error: (error) => {
      console.error('Error fetching accommodation:', error);
      this.errorMessage = 'Failed to load accommodation data. Please try again later.';
      this.isLoading = false; // Set loading to false on error
    }
  });
}
}
