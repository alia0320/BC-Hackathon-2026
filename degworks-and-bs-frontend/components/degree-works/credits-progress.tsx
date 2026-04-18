import { Info } from "lucide-react";

interface CreditCategory {
  name: string;
  completed: number;
  required: number;
  inProgress: number;
  color: string;
}

interface CreditsProgressProps {
  totalCredits: number;
  requiredCredits: number;
  inProgressCredits: number;
  categories: CreditCategory[];
}

export function CreditsProgress({
  totalCredits,
  requiredCredits,
  inProgressCredits,
  categories,
}: CreditsProgressProps) {
  const completionPercentage = Math.min((totalCredits / requiredCredits) * 100, 100);
  const projectedPercentage = Math.min(
    ((totalCredits + inProgressCredits) / requiredCredits) * 100,
    100
  );
  const remaining = requiredCredits - totalCredits - inProgressCredits;

  // Calculate stroke dasharray for the circular progress
  const radius = 42;
  const circumference = 2 * Math.PI * radius;
  const completedDash = (completionPercentage / 100) * circumference;
  const inProgressDash = ((projectedPercentage - completionPercentage) / 100) * circumference;

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border bg-muted/30">
        <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">Credit Progress</h3>
      </div>

      <div className="p-3">
        <div className="flex gap-4">
          {/* Circular Progress */}
          <div className="relative flex-shrink-0">
            <svg className="w-24 h-24 transform -rotate-90">
              {/* Background circle */}
              <circle
                cx="48"
                cy="48"
                r={radius}
                stroke="currentColor"
                strokeWidth="8"
                fill="none"
                className="text-muted"
              />
              {/* In-progress arc */}
              <circle
                cx="48"
                cy="48"
                r={radius}
                stroke="#A5907E"
                strokeWidth="8"
                fill="none"
                strokeDasharray={`${completedDash + inProgressDash} ${circumference}`}
                strokeLinecap="round"
                className="progress-ring"
              />
              {/* Completed arc */}
              <circle
                cx="48"
                cy="48"
                r={radius}
                stroke="#550C18"
                strokeWidth="8"
                fill="none"
                strokeDasharray={`${completedDash} ${circumference}`}
                strokeLinecap="round"
                className="progress-ring"
              />
            </svg>
            <div className="absolute inset-0 flex flex-col items-center justify-center">
              <p className="text-xl font-bold text-foreground">{totalCredits}</p>
              <p className="text-[9px] text-muted-foreground">of {requiredCredits}</p>
            </div>
          </div>

          {/* Stats Grid */}
          <div className="flex-1 grid grid-cols-2 gap-2">
            <div className="stat-card">
              <p className="text-[10px] text-muted-foreground uppercase">Completed</p>
              <p className="text-lg font-bold text-[#550C18]">{totalCredits}</p>
            </div>
            <div className="stat-card">
              <p className="text-[10px] text-muted-foreground uppercase">In Progress</p>
              <p className="text-lg font-bold text-[#A5907E]">{inProgressCredits}</p>
            </div>
            <div className="stat-card">
              <p className="text-[10px] text-muted-foreground uppercase">Remaining</p>
              <p className="text-lg font-bold text-[#786452]">{Math.max(remaining, 0)}</p>
            </div>
            <div className="stat-card">
              <p className="text-[10px] text-muted-foreground uppercase">Progress</p>
              <p className="text-lg font-bold text-foreground">{completionPercentage.toFixed(0)}%</p>
            </div>
          </div>
        </div>

        {/* Category Breakdown */}
        <div className="mt-3 pt-3 border-t border-border space-y-2">
          {categories.map((category) => {
            const catPercentage = (category.completed / category.required) * 100;
            const inProgressPct = (category.inProgress / category.required) * 100;
            const isComplete = catPercentage >= 100;
            
            return (
              <div key={category.name} className="group">
                <div className="flex items-center justify-between text-[11px] mb-0.5">
                  <span className="text-foreground font-medium">{category.name}</span>
                  <span className={isComplete ? "text-[#15803d] font-medium" : "text-muted-foreground"}>
                    {category.completed}/{category.required}
                    {category.inProgress > 0 && (
                      <span className="text-[#A5907E]"> (+{category.inProgress})</span>
                    )}
                  </span>
                </div>
                <div className="h-1.5 bg-muted rounded-full overflow-hidden flex">
                  <div
                    className="h-full bg-[#550C18] transition-all duration-500"
                    style={{ width: `${Math.min(catPercentage, 100)}%` }}
                  />
                  <div
                    className="h-full bg-[#A5907E] transition-all duration-500"
                    style={{ width: `${Math.min(inProgressPct, 100 - catPercentage)}%` }}
                  />
                </div>
              </div>
            );
          })}
        </div>

        {/* Legend */}
        <div className="flex items-center gap-4 mt-3 pt-2 border-t border-border">
          <div className="flex items-center gap-1.5 text-[10px] text-muted-foreground">
            <div className="w-2 h-2 rounded-full bg-[#550C18]" />
            <span>Completed</span>
          </div>
          <div className="flex items-center gap-1.5 text-[10px] text-muted-foreground">
            <div className="w-2 h-2 rounded-full bg-[#A5907E]" />
            <span>In Progress</span>
          </div>
          <div className="flex items-center gap-1.5 text-[10px] text-muted-foreground">
            <div className="w-2 h-2 rounded-full bg-muted" />
            <span>Remaining</span>
          </div>
        </div>
      </div>
    </div>
  );
}
