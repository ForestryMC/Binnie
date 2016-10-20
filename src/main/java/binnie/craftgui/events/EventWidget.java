package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public class EventWidget extends Event {
    public EventWidget(final IWidget origin) {
        super(origin);
    }

    public static class Enable extends EventWidget {
        public Enable(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Enable> {
            public Handler() {
                super(Enable.class);
            }
        }
    }

    public static class Disable extends EventWidget {
        public Disable(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Disable> {
            public Handler() {
                super(Disable.class);
            }
        }
    }

    public static class Show extends EventWidget {
        public Show(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Show> {
            public Handler() {
                super(Show.class);
            }
        }
    }

    public static class Hide extends EventWidget {
        public Hide(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<Hide> {
            public Handler() {
                super(Hide.class);
            }
        }
    }

    public static class ChangePosition extends EventWidget {
        public ChangePosition(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangePosition> {
            public Handler() {
                super(ChangePosition.class);
            }
        }
    }

    public static class ChangeSize extends EventWidget {
        public ChangeSize(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeSize> {
            public Handler() {
                super(ChangeSize.class);
            }
        }
    }

    public static class ChangeOffset extends EventWidget {
        public ChangeOffset(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeOffset> {
            public Handler() {
                super(ChangeOffset.class);
            }
        }
    }

    public static class ChangeColour extends EventWidget {
        public ChangeColour(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<ChangeColour> {
            public Handler() {
                super(ChangeColour.class);
            }
        }
    }

    public static class StartMouseOver extends EventWidget {
        public StartMouseOver(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<StartMouseOver> {
            public Handler() {
                super(StartMouseOver.class);
            }
        }
    }

    public static class EndMouseOver extends EventWidget {
        public EndMouseOver(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<EndMouseOver> {
            public Handler() {
                super(EndMouseOver.class);
            }
        }
    }

    public static class StartDrag extends EventWidget {
        int button;

        public StartDrag(final IWidget origin, final int button) {
            super(origin);
            this.button = button;
        }

        public int getButton() {
            return this.button;
        }

        public abstract static class Handler extends EventHandler<StartDrag> {
            public Handler() {
                super(StartDrag.class);
            }
        }
    }

    public static class EndDrag extends EventWidget {
        public EndDrag(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<EndDrag> {
            public Handler() {
                super(EndDrag.class);
            }
        }
    }

    public static class GainFocus extends EventWidget {
        public GainFocus(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<GainFocus> {
            public Handler() {
                super(GainFocus.class);
            }
        }
    }

    public static class LoseFocus extends EventWidget {
        public LoseFocus(final IWidget origin) {
            super(origin);
        }

        public abstract static class Handler extends EventHandler<LoseFocus> {
            public Handler() {
                super(LoseFocus.class);
            }
        }
    }
}
