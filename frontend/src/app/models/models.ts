export interface Product {
  productId: string;
  productName: string;
  description: string;
  category: string;
  keyFeatures: string;
}

export interface ProductAnalysisResponse {
  productId: string;
  productName: string;
  analysis: string;
  templateUsed: string;
}

export interface ProductComparisonRequest {
  productIdA: string;
  productIdB: string;
  comparisonType: string;
}

export interface ProductComparisonResponse {
  productAName: string;
  productBName: string;
  comparison: string;
  comparisonType: string;
  templateUsed: string;
}

export interface DocumentSummaryResponse {
  fileName: string;
  summary: string;
  totalPages: number;
  templateUsed: string;
}

export interface HealthInsightsResponse {
  insights: string;
  totalProducts: number;
  categories: string[];
  templateUsed: string;
}

export interface WellnessRecommendationResponse {
  productName: string;
  category: string;
  recommendations: string;
  templateUsed: string;
}

export interface DashboardSummary {
  totalProducts: number;
  categories: string[];
  categoryDistribution: { [key: string]: number };
  products: Product[];
}

export interface ChatbotResponse {
  reply: string;
  sessionId: string;
}
