import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Product,
  ProductAnalysisResponse,
  ProductComparisonRequest,
  ProductComparisonResponse,
  DocumentSummaryResponse,
  HealthInsightsResponse,
  WellnessRecommendationResponse,
  DashboardSummary,
  ChatbotResponse
} from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/products`);
  }

  getProduct(identifier: string): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/products/${identifier}`);
  }

  analyzeProduct(identifier: string): Observable<ProductAnalysisResponse> {
    return this.http.get<ProductAnalysisResponse>(`${this.baseUrl}/analysis/${identifier}`);
  }

  analyzeProductBrief(identifier: string): Observable<ProductAnalysisResponse> {
    return this.http.get<ProductAnalysisResponse>(`${this.baseUrl}/analysis/${identifier}/brief`);
  }

  compareProducts(request: ProductComparisonRequest): Observable<ProductComparisonResponse> {
    return this.http.post<ProductComparisonResponse>(`${this.baseUrl}/comparison`, request);
  }

  compareByCategory(category: string): Observable<ProductComparisonResponse> {
    return this.http.get<ProductComparisonResponse>(`${this.baseUrl}/comparison/category/${category}`);
  }

  summarizeDocument(file: File): Observable<DocumentSummaryResponse> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<DocumentSummaryResponse>(`${this.baseUrl}/documents/summarize`, formData);
  }

  summarizeDocumentBrief(file: File): Observable<DocumentSummaryResponse> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<DocumentSummaryResponse>(`${this.baseUrl}/documents/summarize/brief`, formData);
  }

  getDashboardSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(`${this.baseUrl}/dashboard/summary`);
  }

  getHealthInsights(): Observable<HealthInsightsResponse> {
    return this.http.get<HealthInsightsResponse>(`${this.baseUrl}/dashboard/insights`);
  }

  getWellnessRecommendations(identifier: string): Observable<WellnessRecommendationResponse> {
    return this.http.get<WellnessRecommendationResponse>(`${this.baseUrl}/dashboard/wellness/${identifier}`);
  }

  chatbotChat(message: string, sessionId: string | null): Observable<ChatbotResponse> {
    return this.http.post<ChatbotResponse>(`${this.baseUrl}/chatbot/chat`, { message, sessionId });
  }
}
