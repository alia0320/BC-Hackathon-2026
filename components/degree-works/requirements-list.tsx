import { CheckCircle2, Circle, Clock, AlertTriangle, ChevronDown, ChevronRight } from "lucide-react";
import { useState } from "react";
import { Badge } from "@/components/ui/badge";

type RequirementStatus = "completed" | "in-progress" | "not-started" | "at-risk";

interface Requirement {
  id: string;
  name: string;
  status: RequirementStatus;
  courses: {
    code: string;
    name: string;
    credits: number;
    grade?: string;
    semester?: string;
  }[];
  notes?: string;
  creditsRequired?: number;
  creditsCompleted?: number;
}

interface RequirementsListProps {
  requirements: Requirement[];
  compact?: boolean;
}

const statusConfig = {
  completed: {
    icon: CheckCircle2,
    color: "text-[#15803d]",
    bgColor: "bg-[#dcfce7]",
    borderColor: "border-[#15803d]/20",
    label: "Complete",
  },
  "in-progress": {
    icon: Clock,
    color: "text-[#0369a1]",
    bgColor: "bg-[#e0f2fe]",
    borderColor: "border-[#0369a1]/20",
    label: "In Progress",
  },
  "not-started": {
    icon: Circle,
    color: "text-[#786452]",
    bgColor: "bg-muted",
    borderColor: "border-border",
    label: "Not Started",
  },
  "at-risk": {
    icon: AlertTriangle,
    color: "text-[#b91c1c]",
    bgColor: "bg-red-50",
    borderColor: "border-[#b91c1c]/20",
    label: "At Risk",
  },
};

export function RequirementsList({ requirements, compact = false }: RequirementsListProps) {
  const [expandedIds, setExpandedIds] = useState<Set<string>>(new Set(["1", "2"]));

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

  const completedCount = requirements.filter(r => r.status === "completed").length;
  const totalCount = requirements.length;

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border bg-muted/30 flex items-center justify-between">
        <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">Degree Requirements</h3>
        <span className="text-[10px] text-muted-foreground">
          {completedCount}/{totalCount} complete
        </span>
      </div>

      <div className={compact ? "max-h-[400px] overflow-y-auto" : ""}>
        {requirements.map((req) => {
          const config = statusConfig[req.status];
          const StatusIcon = config.icon;
          const isExpanded = expandedIds.has(req.id);
          const ChevronIcon = isExpanded ? ChevronDown : ChevronRight;

          return (
            <div key={req.id} className={`border-b border-border last:border-0 ${config.borderColor}`}>
              {/* Requirement Header */}
              <button
                onClick={() => toggleExpanded(req.id)}
                className="w-full flex items-center gap-2 px-3 py-2 hover:bg-muted/30 transition-colors text-left"
              >
                <ChevronIcon className="h-3.5 w-3.5 text-muted-foreground flex-shrink-0" />
                <StatusIcon className={`h-4 w-4 ${config.color} flex-shrink-0`} />
                <span className="flex-1 text-sm font-medium text-foreground truncate">{req.name}</span>
                <Badge className={`${config.bgColor} ${config.color} border-0 text-[10px] px-1.5 h-4`}>
                  {config.label}
                </Badge>
              </button>

              {/* Expanded Content */}
              {isExpanded && (
                <div className="bg-muted/20 border-t border-border">
                  {req.courses.length > 0 && (
                    <div className="divide-y divide-border">
                      {req.courses.map((course, idx) => (
                        <div key={idx} className="flex items-center gap-2 px-3 py-1.5 pl-9 text-[11px]">
                          <span className="font-mono font-medium text-foreground w-16">{course.code}</span>
                          <span className="flex-1 text-muted-foreground truncate">{course.name}</span>
                          <span className="text-muted-foreground w-8 text-right">{course.credits}cr</span>
                          {course.grade ? (
                            <Badge 
                              variant="secondary"
                              className={`w-7 justify-center text-[10px] h-4 px-0 ${
                                course.grade.startsWith("A") ? "bg-[#dcfce7] text-[#15803d]" :
                                course.grade.startsWith("B") ? "bg-[#F7DAD9] text-[#550C18]" :
                                course.grade.startsWith("C") ? "bg-[#fef9c3] text-[#ca8a04]" :
                                "bg-red-50 text-[#b91c1c]"
                              }`}
                            >
                              {course.grade}
                            </Badge>
                          ) : course.semester === "Current" ? (
                            <Badge variant="outline" className="text-[10px] h-4 px-1 border-[#0369a1] text-[#0369a1]">
                              Now
                            </Badge>
                          ) : course.semester ? (
                            <span className="text-[10px] text-muted-foreground w-12 text-right">{course.semester}</span>
                          ) : (
                            <span className="w-7" />
                          )}
                        </div>
                      ))}
                    </div>
                  )}
                  
                  {req.notes && (
                    <div className={`px-3 py-1.5 pl-9 text-[10px] ${
                      req.status === "at-risk" ? "text-[#b91c1c] bg-red-50/50" : "text-muted-foreground"
                    }`}>
                      {req.notes}
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
