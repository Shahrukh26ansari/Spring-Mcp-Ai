import { Injectable }      from '@angular/core';
import { HttpClient }      from '@angular/common/http';
import { Observable }      from 'rxjs';
import { AiProvider, QueryRequest, QueryResponse } from '../models/query.model';

@Injectable({ providedIn: 'root' })
export class QueryService {

  private readonly base = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  ask(question: string, provider?: string): Observable<QueryResponse> {
    const body: QueryRequest = { question, ...(provider ? { provider } : {}) };
    return this.http.post<QueryResponse>(`${this.base}/query`, body);
  }

  getProviders(): Observable<AiProvider[]> {
    return this.http.get<AiProvider[]>(`${this.base}/providers`);
  }
}
