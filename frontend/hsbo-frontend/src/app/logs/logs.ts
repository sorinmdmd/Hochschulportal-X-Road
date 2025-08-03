import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


interface LogEntry {
  log_time: string;
  queryid: string;
  http_method: string;
  url_path: string;
  message_flattened: string;
  xRoadClient: string | null; // <- Änderung hier
}

@Component({
  selector: 'app-logs',
  standalone: false,
  templateUrl: './logs.html',
  styleUrl: './logs.css'
})
export class Logs implements OnInit{
  logs: LogEntry[] = [];
  errorMessage: string = '';
  private apiUrl = 'http://localhost:8085/api/x-road/housingUnits/getLogs';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchLogs();
  }

  fetchLogs(): void {
    this.http.get<LogEntry[]>(this.apiUrl)
      .pipe(
        catchError(error => {
          console.error('Error fetching logs:', error);
          this.errorMessage = 'Failed to load logs. Please ensure the backend server is running and accessible at ' + this.apiUrl + '.';
          return throwError(() => new Error(this.errorMessage));
        })
      )
      .subscribe(data => {
        // Sort by descending time and extract X-Road-Client
        this.logs = data.map(log => ({
          ...log,
          xRoadClient: this.extractXRoadClient(log.message_flattened)
        })).sort((a, b) => new Date(b.log_time).getTime() - new Date(a.log_time).getTime());

        this.errorMessage = '';
      });
  }

  private extractXRoadClient(message: string): string | null {
    const match = message.match(/X-Road-Client:([\w/-]+)/);
    return match ? match[1] : null;
  }
}
