import { TrendingUp, TrendingDown, Minus, Target } from "lucide-react";

interface GPACardProps {
  cumulativeGPA: number;
  majorGPA: number;
  semesterGPA: number;
  trend: "up" | "down" | "stable";
  targetGPA?: number;
  creditsAttempted: number;
  qualityPoints: number;
}

export function GPACard({ 
  cumulativeGPA, 
  majorGPA, 
  semesterGPA, 
  trend, 
  targetGPA = 3.5,
  creditsAttempted,
  qualityPoints 
}: GPACardProps) {
  const getGPAColor = (gpa: number) => {
    if (gpa >= 3.7) return "text-[#15803d]";
    if (gpa >= 3.0) return "text-[#550C18]";
    if (gpa >= 2.5) return "text-[#ca8a04]";
    return "text-[#b91c1c]";
  };

  const getGPABg = (gpa: number) => {
    if (gpa >= 3.7) return "bg-[#dcfce7]";
    if (gpa >= 3.0) return "bg-[#F7DAD9]";
    if (gpa >= 2.5) return "bg-[#fef9c3]";
    return "bg-red-50";
  };

  const TrendIcon = trend === "up" ? TrendingUp : trend === "down" ? TrendingDown : Minus;
  const trendColor = trend === "up" ? "text-[#15803d]" : trend === "down" ? "text-[#b91c1c]" : "text-[#786452]";

  const progressToTarget = Math.min((cumulativeGPA / targetGPA) * 100, 100);

  return (
    <div className="bg-card border border-border rounded-lg overflow-hidden">
      {/* Header */}
      <div className="px-3 py-2 border-b border-border flex items-center justify-between bg-muted/30">
        <h3 className="text-xs font-semibold text-foreground uppercase tracking-wide">GPA Overview</h3>
        <div className={`flex items-center gap-1 text-[10px] ${trendColor}`}>
          <TrendIcon className="h-3 w-3" />
          <span>{trend === "up" ? "+0.08" : trend === "down" ? "-0.05" : "0.00"}</span>
        </div>
      </div>

      <div className="p-3">
        {/* Main GPA Display */}
        <div className={`${getGPABg(cumulativeGPA)} rounded-lg p-3 mb-3`}>
          <div className="flex items-center justify-between">
            <div>
              <p className="text-[10px] text-muted-foreground uppercase tracking-wide">Cumulative</p>
              <p className={`text-3xl font-bold ${getGPAColor(cumulativeGPA)}`}>
                {cumulativeGPA.toFixed(2)}
              </p>
            </div>
            <div className="text-right">
              <p className="text-[10px] text-muted-foreground">of 4.00</p>
              <p className="text-xs text-muted-foreground mt-1">{creditsAttempted} credits</p>
              <p className="text-[10px] text-muted-foreground">{qualityPoints.toFixed(1)} QP</p>
            </div>
          </div>
        </div>

        {/* Secondary GPAs */}
        <div className="grid grid-cols-2 gap-2 mb-3">
          <div className="stat-card text-center">
            <p className="text-[10px] text-muted-foreground uppercase">Major</p>
            <p className={`text-lg font-bold ${getGPAColor(majorGPA)}`}>{majorGPA.toFixed(2)}</p>
          </div>
          <div className="stat-card text-center">
            <p className="text-[10px] text-muted-foreground uppercase">Semester</p>
            <p className={`text-lg font-bold ${getGPAColor(semesterGPA)}`}>{semesterGPA.toFixed(2)}</p>
          </div>
        </div>

        {/* Target Progress */}
        <div className="space-y-1">
          <div className="flex items-center justify-between text-[10px]">
            <div className="flex items-center gap-1 text-muted-foreground">
              <Target className="h-3 w-3" />
              <span>Dean&apos;s List Target: {targetGPA.toFixed(2)}</span>
            </div>
            <span className={progressToTarget >= 100 ? "text-[#15803d] font-medium" : "text-muted-foreground"}>
              {progressToTarget >= 100 ? "Achieved" : `${progressToTarget.toFixed(0)}%`}
            </span>
          </div>
          <div className="h-1.5 bg-muted rounded-full overflow-hidden">
            <div
              className={`h-full transition-all duration-500 ${progressToTarget >= 100 ? "bg-[#15803d]" : "bg-[#550C18]"}`}
              style={{ width: `${progressToTarget}%` }}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
