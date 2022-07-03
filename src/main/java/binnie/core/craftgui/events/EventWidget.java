package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventWidget extends Event {
    public EventWidget(IWidget origin) {
        super(origin);
    }

    public static class Enable extends EventWidget {
        public Enable(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Enable> {
            public Handler() {
                super(Enable.class);
            }
        }
    }

    public static class Disable extends EventWidget {
        public Disable(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Disable> {
            public Handler() {
                super(Disable.class);
            }
        }
    }

    public static class Show extends EventWidget {
        public Show(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Show> {
            public Handler() {
                super(Show.class);
            }
        }
    }

    public static class Hide extends EventWidget {
        public Hide(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Hide> {
            public Handler() {
                super(Hide.class);
            }
        }
    }

    public static class ChangePosition extends EventWidget {
        public ChangePosition(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangePosition> {
            public Handler() {
                super(ChangePosition.class);
            }
        }
    }

    public static class ChangeSize extends EventWidget {
        public ChangeSize(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeSize> {
            public Handler() {
                super(ChangeSize.class);
            }
        }
    }

    public static class ChangeOffset extends EventWidget {
        public ChangeOffset(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeOffset> {
            public Handler() {
                super(ChangeOffset.class);
            }
        }
    }

    public static class ChangeColour extends EventWidget {
        public ChangeColour(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeColour> {
            public Handler() {
                super(ChangeColour.class);
            }
        }
    }

    public static class StartMouseOver extends EventWidget {
        public StartMouseOver(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<StartMouseOver> {
            public Handler() {
                super(StartMouseOver.class);
            }
        }
    }

    public static class EndMouseOver extends EventWidget {
        public EndMouseOver(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<EndMouseOver> {
            public Handler() {
                super(EndMouseOver.class);
            }
        }
    }

    public static class StartDrag extends EventWidget {
        protected int button;

        public StartDrag(IWidget origin, int button) {
            super(origin);
            this.button = button;
        }

        public int getButton() {
            return button;
        }

        public abstract static class Handler extends EventHandler<StartDrag> {
            public Handler() {
                super(StartDrag.class);
            }
        }
    }

    public static class EndDrag extends EventWidget {
        public EndDrag(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<EndDrag> {
            public Handler() {
                super(EndDrag.class);
            }
        }
    }

    public static class GainFocus extends EventWidget {
        public GainFocus(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<GainFocus> {
            public Handler() {
                super(GainFocus.class);
            }
        }
    }

    public static class LoseFocus extends EventWidget {
        public LoseFocus(IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<LoseFocus> {
            public Handler() {
                super(LoseFocus.class);
            }
        }
    }
}
