/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import java.util.*;
import Project5.analysis.*;

@SuppressWarnings("nls")
public final class AVariabledefClassmethodstmt extends PClassmethodstmt
{
    private TId _id_;
    private final LinkedList<PIdtail> _idtail_ = new LinkedList<PIdtail>();
    private TColon _colon_;
    private PType _type_;
    private TSemicolon _semicolon_;

    public AVariabledefClassmethodstmt()
    {
        // Constructor
    }

    public AVariabledefClassmethodstmt(
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") List<?> _idtail_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TSemicolon _semicolon_)
    {
        // Constructor
        setId(_id_);

        setIdtail(_idtail_);

        setColon(_colon_);

        setType(_type_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new AVariabledefClassmethodstmt(
            cloneNode(this._id_),
            cloneList(this._idtail_),
            cloneNode(this._colon_),
            cloneNode(this._type_),
            cloneNode(this._semicolon_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVariabledefClassmethodstmt(this);
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public LinkedList<PIdtail> getIdtail()
    {
        return this._idtail_;
    }

    public void setIdtail(List<?> list)
    {
        for(PIdtail e : this._idtail_)
        {
            e.parent(null);
        }
        this._idtail_.clear();

        for(Object obj_e : list)
        {
            PIdtail e = (PIdtail) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._idtail_.add(e);
        }
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._idtail_)
            + toString(this._colon_)
            + toString(this._type_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._idtail_.remove(child))
        {
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        for(ListIterator<PIdtail> i = this._idtail_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PIdtail) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
