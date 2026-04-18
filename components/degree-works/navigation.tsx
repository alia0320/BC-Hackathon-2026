import { LayoutDashboard, BookOpen, Users, CalendarDays, Settings, HelpCircle, Download } from "lucide-react";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";

interface NavigationProps {
  activeTab: string;
  onTabChange: (tab: string) => void;
}

const tabs = [
  { id: "overview", label: "Overview", icon: LayoutDashboard },
  { id: "courses", label: "Courses", icon: BookOpen },
  { id: "professors", label: "Professors", icon: Users },
  { id: "planner", label: "Planner", icon: CalendarDays },
];

export function Navigation({ activeTab, onTabChange }: NavigationProps) {
  return (
    <nav className="bg-card border-b border-border sticky top-0 z-10">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-0.5 overflow-x-auto">
            {tabs.map((tab) => {
              const Icon = tab.icon;
              const isActive = activeTab === tab.id;

              return (
                <button
                  key={tab.id}
                  onClick={() => onTabChange(tab.id)}
                  className={cn(
                    "flex items-center gap-1.5 px-3 py-2.5 text-xs font-medium border-b-2 transition-colors whitespace-nowrap",
                    isActive
                      ? "border-[#550C18] text-[#550C18]"
                      : "border-transparent text-muted-foreground hover:text-foreground hover:bg-muted/50"
                  )}
                >
                  <Icon className="h-3.5 w-3.5" />
                  {tab.label}
                </button>
              );
            })}
          </div>

          <div className="flex items-center gap-1">
            <Button variant="ghost" size="sm" className="h-7 px-2 text-[10px] text-muted-foreground">
              <Download className="h-3 w-3 mr-1" />
              Export
            </Button>
            <Button variant="ghost" size="sm" className="h-7 px-2 text-[10px] text-muted-foreground">
              <HelpCircle className="h-3 w-3 mr-1" />
              Help
            </Button>
            <Button variant="ghost" size="sm" className="h-7 px-2 text-[10px] text-muted-foreground">
              <Settings className="h-3.5 w-3.5" />
            </Button>
          </div>
        </div>
      </div>
    </nav>
  );
}
