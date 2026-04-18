import { Star, ThumbsUp, Flame, MessageCircle, ChevronDown, ChevronRight, ExternalLink } from "lucide-react";
import { useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";

interface ProfessorReview {
  id: string;
  course: string;
  rating: number;
  difficulty: number;
  comment: string;
  date: string;
  helpful: number;
  tags: string[];
  grade?: string;
}

interface Professor {
  id: string;
  name: string;
  department: string;
  overallRating: number;
  wouldTakeAgain: number;
  difficultyLevel: number;
  totalRatings: number;
  topTags: string[];
  recentReviews: ProfessorReview[];
  courses?: string[];
}

interface ProfessorRatingsProps {
  professors: Professor[];
  compact?: boolean;
  highlightedProfessorId?: string;
}

function RatingBadge({ rating }: { rating: number }) {
  const getColor = (r: number) => {
    if (r >= 4) return "bg-[#15803d] text-white";
    if (r >= 3) return "bg-[#ca8a04] text-white";
    if (r >= 2) return "bg-[#ea580c] text-white";
    return "bg-[#b91c1c] text-white";
  };

  return (
    <div className={`${getColor(rating)} font-bold text-base w-10 h-10 rounded-lg flex items-center justify-center`}>
      {rating.toFixed(1)}
    </div>
  );
}

function MiniStars({ rating }: { rating: number }) {
  return (
    <div className="flex items-center gap-0.5">
      {Array.from({ length: 5 }).map((_, i) => (
        <Star
          key={i}
          className={`h-3 w-3 ${
            i < Math.floor(rating)
              ? "fill-[#ca8a04] text-[#ca8a04]"
              : i < rating
              ? "fill-[#ca8a04]/50 text-[#ca8a04]"
              : "text-muted fill-none"
          }`}
        />
      ))}
    </div>
  );
}

function DifficultyBar({ level }: { level: number }) {
  const segments = 5;
  const filled = Math.round(level);
  
  return (
    <div className="flex items-center gap-0.5">
      {Array.from({ length: segments }).map((_, i) => (
        <div
          key={i}
          className={`h-1.5 w-3 rounded-sm ${
            i < filled
              ? level >= 4 ? "bg-[#b91c1c]" : level >= 3 ? "bg-[#ca8a04]" : "bg-[#15803d]"
              : "bg-muted"
          }`}
        />
      ))}
    </div>
  );
}

export function ProfessorRatings({ professors, compact = false, highlightedProfessorId }: ProfessorRatingsProps) {
  const [expandedIds, setExpandedIds] = useState<Set<string>>(new Set([highlightedProfessorId || ""]));

  const toggleExpanded = (id: string) => {
    setExpandedIds(prev => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  };

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border bg-muted/30 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <Star className="h-4 w-4 text-[#ca8a04] fill-[#ca8a04]" />
          <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">Professor Ratings</h3>
          <Badge variant="secondary" className="text-[10px] h-4 px-1.5 bg-[#fef9c3] text-[#ca8a04]">
            RateMyProfessor
          </Badge>
        </div>
        <Button variant="ghost" size="sm" className="text-[#550C18] h-6 px-2 text-[10px]">
          View All <ExternalLink className="h-3 w-3 ml-1" />
        </Button>
      </div>

      <div className={compact ? "max-h-[450px] overflow-y-auto" : ""}>
        {professors.map((professor) => {
          const isExpanded = expandedIds.has(professor.id);
          const isHighlighted = professor.id === highlightedProfessorId;
          const ChevronIcon = isExpanded ? ChevronDown : ChevronRight;

          return (
            <div 
              key={professor.id} 
              className={`border-b border-border last:border-0 ${isHighlighted ? "bg-[#F7DAD9]/30" : ""}`}
            >
              {/* Professor Header */}
              <button
                onClick={() => toggleExpanded(professor.id)}
                className="w-full flex items-center gap-3 px-3 py-2.5 hover:bg-muted/30 transition-colors text-left"
              >
                <ChevronIcon className="h-3.5 w-3.5 text-muted-foreground flex-shrink-0" />
                
                <Avatar className="h-9 w-9 flex-shrink-0">
                  <AvatarFallback className="bg-[#550C18]/10 text-[#550C18] text-xs font-semibold">
                    {professor.name.split(" ").map((n) => n[0]).join("")}
                  </AvatarFallback>
                </Avatar>

                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <span className="font-semibold text-sm text-foreground">{professor.name}</span>
                  </div>
                  <p className="text-[10px] text-muted-foreground">{professor.department}</p>
                </div>

                <RatingBadge rating={professor.overallRating} />
              </button>

              {/* Expanded Content */}
              {isExpanded && (
                <div className="bg-muted/20 border-t border-border">
                  {/* Stats Row */}
                  <div className="px-3 py-2 grid grid-cols-3 gap-3 border-b border-border">
                    <div className="text-center">
                      <div className="flex items-center justify-center gap-1 text-[#15803d]">
                        <ThumbsUp className="h-3 w-3" />
                        <span className="font-bold text-sm">{professor.wouldTakeAgain}%</span>
                      </div>
                      <p className="text-[9px] text-muted-foreground">Would Retake</p>
                    </div>
                    <div className="text-center">
                      <div className="flex items-center justify-center gap-1 text-[#ea580c]">
                        <Flame className="h-3 w-3" />
                        <span className="font-bold text-sm">{professor.difficultyLevel.toFixed(1)}</span>
                      </div>
                      <p className="text-[9px] text-muted-foreground">Difficulty</p>
                    </div>
                    <div className="text-center">
                      <div className="flex items-center justify-center gap-1 text-muted-foreground">
                        <MessageCircle className="h-3 w-3" />
                        <span className="font-bold text-sm">{professor.totalRatings}</span>
                      </div>
                      <p className="text-[9px] text-muted-foreground">Reviews</p>
                    </div>
                  </div>

                  {/* Tags */}
                  <div className="px-3 py-2 flex flex-wrap gap-1 border-b border-border">
                    {professor.topTags.slice(0, 5).map((tag) => (
                      <Badge
                        key={tag}
                        variant="secondary"
                        className="text-[9px] h-4 px-1.5 bg-[#550C18]/10 text-[#550C18] border-0"
                      >
                        {tag}
                      </Badge>
                    ))}
                  </div>

                  {/* Reviews */}
                  {professor.recentReviews.length > 0 && (
                    <div className="divide-y divide-border">
                      {professor.recentReviews.slice(0, 2).map((review) => (
                        <div key={review.id} className="px-3 py-2">
                          <div className="flex items-center justify-between mb-1">
                            <div className="flex items-center gap-2">
                              <Badge variant="outline" className="text-[9px] h-4 px-1 font-mono">
                                {review.course}
                              </Badge>
                              <MiniStars rating={review.rating} />
                              <DifficultyBar level={review.difficulty} />
                            </div>
                            <span className="text-[9px] text-muted-foreground">{review.date}</span>
                          </div>
                          <p className="text-[11px] text-foreground line-clamp-2 mb-1">
                            {review.comment}
                          </p>
                          <div className="flex items-center gap-2">
                            <button className="flex items-center gap-1 text-[9px] text-muted-foreground hover:text-foreground transition-colors">
                              <ThumbsUp className="h-2.5 w-2.5" />
                              {review.helpful}
                            </button>
                            {review.grade && (
                              <span className="text-[9px] text-muted-foreground">
                                Grade: <span className="font-medium">{review.grade}</span>
                              </span>
                            )}
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
